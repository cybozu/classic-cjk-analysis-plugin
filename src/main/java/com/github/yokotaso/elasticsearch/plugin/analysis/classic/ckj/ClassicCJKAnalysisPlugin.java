package com.github.yokotaso.elasticsearch.plugin.analysis.classic.ckj;

import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.elasticsearch.index.analysis.AnalyzerProvider;
import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import static java.util.Collections.*;

public class ClassicCJKAnalysisPlugin extends Plugin implements AnalysisPlugin {
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return singletonMap("classic_cjk_tokenizer", ClassicCJKTokenizerFactory::new);
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        return singletonMap("classic_cjk_analyzer", ClassicCJKAnalyzerProvider::new);
    }
}
