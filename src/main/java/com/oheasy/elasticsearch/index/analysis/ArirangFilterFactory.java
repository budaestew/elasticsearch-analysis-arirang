/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.oheasy.elasticsearch.index.analysis;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ko.KoreanFilterFactory;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.settings.IndexSettingsService;

import java.util.HashMap;

public class ArirangFilterFactory extends AbstractTokenFilterFactory {
    private KoreanFilterFactory koreanFilterFactory;

    @Inject
    public ArirangFilterFactory(Index index, IndexSettingsService indexSettingsService, String name, Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        HashMap<String, String> args = new HashMap<>();
        args.put("bigrammable", Boolean.toString(getBigrammable(settings)));
        args.put("hasOrigin", Boolean.toString(getHasOrigin(settings)));
        args.put("hasCNoun", Boolean.toString(getHasCNoun(settings)));
        args.put("queryMode", Boolean.toString(getQueryMode(settings)));
        args.put("exactMatch", Boolean.toString(getExactMatch(settings)));

        koreanFilterFactory = new KoreanFilterFactory(args);
    }

    public static boolean getBigrammable(Settings settings) {
        return settings.getAsBoolean("bigrammable", true);
    }

    public static boolean getHasOrigin(Settings settings) {
        return settings.getAsBoolean("has_origin", true);
    }

    public static boolean getHasCNoun(Settings settings) {
        return settings.getAsBoolean("has_cnoun", false);
    }

    public static boolean getQueryMode(Settings settings) {
        return settings.getAsBoolean("query_mode", true);
    }

    public static boolean getExactMatch(Settings settings) {
        return settings.getAsBoolean("exact_match", true);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return koreanFilterFactory.create(tokenStream);
    }
}
