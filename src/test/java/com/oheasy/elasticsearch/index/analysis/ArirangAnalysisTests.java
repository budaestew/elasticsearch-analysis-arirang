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
import org.apache.lucene.analysis.ko.KoreanTokenizer;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.analysis.CustomAnalyzer;
import org.elasticsearch.index.analysis.NamedAnalyzer;
import org.junit.Test;

import java.io.StringReader;

import static org.hamcrest.Matchers.instanceOf;

public class ArirangAnalysisTests extends AnalysisTestCase {
    @Test
    public void testArirangAnalysis() throws Exception {
        AnalysisService analysisService = createAnalysisService();

        assertThat(analysisService.tokenizer("test_arirang_tokenizer"), instanceOf(ArirangTokenizerFactory.class));

        assertThat(analysisService.tokenFilter("test_arirang_korean"), instanceOf(ArirangFilterFactory.class));

        assertThat(analysisService.tokenFilter("test_arirang_hanja"), instanceOf(HanjaMappingFilterFactory.class));

        assertThat(analysisService.tokenFilter("test_arirang_punctuation"), instanceOf(PunctuationDelimitFilterFactory.class));

        assertThat(analysisService.tokenFilter("test_arirang_word_segment"), instanceOf(WordSegmentFilterFactory.class));

        NamedAnalyzer analyzer = analysisService.analyzer("arirang");
        assertThat(analyzer.analyzer(), instanceOf(KoreanAnalyzer.class));

        analyzer = analysisService.analyzer("test_arirang_custom");
        assertThat(analyzer.analyzer(), instanceOf(CustomAnalyzer.class));
        assertThat(analyzer.analyzer().tokenStream(null, new StringReader("")), instanceOf(KoreanTokenizer.class));
    }
}
