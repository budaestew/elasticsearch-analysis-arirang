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

package com.oheasy.elasticsearch.plugin.analysis.arirang;

import com.oheasy.elasticsearch.index.analysis.*;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.Plugin;

import java.util.Collection;

public class AnalysisArirangPlugin extends Plugin {
    @Override
    public String name() {
        return "analysis-arirang";
    }

    @Override
    public String description() {
        return "Arirang analysis support";
    }

    @Override
    public Collection<Module> nodeModules() {
        return super.nodeModules();
    }

    public void onModule(AnalysisModule module) {
        module.addAnalyzer("arirang", ArirangAnalyzerProvider.class);
        module.addTokenizer("arirang_tokenizer", ArirangTokenizerFactory.class);
        module.addTokenFilter("arirang_korean", ArirangFilterFactory.class);
        module.addTokenFilter("arirang_hanja", HanjaMappingFilterFactory.class);
        module.addTokenFilter("arirang_punctuation", PunctuationDelimitFilterFactory.class);
        module.addTokenFilter("arirang_word_segment", WordSegmentFilterFactory.class);
    }
}
