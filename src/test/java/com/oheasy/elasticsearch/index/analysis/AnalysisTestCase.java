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

import com.oheasy.elasticsearch.plugin.analysis.arirang.AnalysisArirangPlugin;
import org.elasticsearch.Version;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.inject.ModulesBuilder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsModule;
import org.elasticsearch.env.Environment;
import org.elasticsearch.env.EnvironmentModule;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.IndexNameModule;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.index.analysis.AnalysisService;
import org.elasticsearch.index.settings.IndexSettingsModule;
import org.elasticsearch.indices.analysis.IndicesAnalysisService;
import org.elasticsearch.test.ESTestCase;

public class AnalysisTestCase extends ESTestCase {
    private static final String TEST_JSON = "/com/oheasy/elasticsearch/index/analysis/arirang_analysis.json";

    public static AnalysisService createAnalysisService() {
        Settings settings = Settings.settingsBuilder()
                .put("path.home", createTempDir())
                .loadFromStream(TEST_JSON, AnalysisTestCase.class.getResourceAsStream(TEST_JSON))
                .put(IndexMetaData.SETTING_VERSION_CREATED, Version.CURRENT)
                .build();

        Index index = new Index("test");

        Injector parentInjector = new ModulesBuilder().add(
                new SettingsModule(settings),
                new EnvironmentModule(new Environment(settings)))
                .createInjector();

        AnalysisModule analysisModule = new AnalysisModule(settings, parentInjector.getInstance(IndicesAnalysisService.class));
        new AnalysisArirangPlugin().onModule(analysisModule);

        Injector injector = new ModulesBuilder().add(
                new IndexSettingsModule(index, settings),
                new IndexNameModule(index),
                analysisModule)
                .createChildInjector(parentInjector);

        return injector.getInstance(AnalysisService.class);
    }
}
