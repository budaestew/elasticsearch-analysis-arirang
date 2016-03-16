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

import org.apache.lucene.analysis.ko.KoreanAnalyzer;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettingsService;

public class ArirangAnalyzerProvider extends AbstractIndexAnalyzerProvider<KoreanAnalyzer> {
    private final KoreanAnalyzer koreanAnalyzer;

    @Inject
    public ArirangAnalyzerProvider(Index index, IndexSettingsService indexSettingsService, @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettingsService.getSettings(), name, settings);
        koreanAnalyzer = new KoreanAnalyzer();
        koreanAnalyzer.setBigrammable(ArirangFilterFactory.getBigrammable(settings));
        koreanAnalyzer.setHasOrigin(ArirangFilterFactory.getHasOrigin(settings));
        koreanAnalyzer.setExactMatch(ArirangFilterFactory.getExactMatch(settings));
        koreanAnalyzer.setOriginCNoun(ArirangFilterFactory.getHasCNoun(settings));
        koreanAnalyzer.setQueryMode(ArirangFilterFactory.getQueryMode(settings));
        koreanAnalyzer.setWordSegment(settings.getAsBoolean("word_segment", false));
    }

    @Override
    public KoreanAnalyzer get() {
        return this.koreanAnalyzer;
    }
}
