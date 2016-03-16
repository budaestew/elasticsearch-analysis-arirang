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
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.settings.IndexSettingsService;

import java.util.HashMap;

public class WordSegmentFilterFactory extends AbstractTokenFilterFactory {
    org.apache.lucene.analysis.ko.WordSegmentFilterFactory wordSegmentFilterFactory;

    @Inject
    public WordSegmentFilterFactory(Index index, IndexSettingsService indexSettingsService, String name, Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        HashMap<String, String> args = new HashMap<>();
        args.put("hasOrigin", Boolean.toString(settings.getAsBoolean("has_origin", true)));
        wordSegmentFilterFactory = new org.apache.lucene.analysis.ko.WordSegmentFilterFactory(args);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return wordSegmentFilterFactory.create(tokenStream);
    }
}
