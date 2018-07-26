package com.github.yokotaso.elasticsearch.plugin.classic.ckj.tokenizer;

import java.util.Map;

import org.elasticsearch.index.analysis.TokenizerFactory;
import org.elasticsearch.indices.analysis.AnalysisModule;
import org.elasticsearch.plugins.AnalysisPlugin;
import org.elasticsearch.plugins.Plugin;

import static java.util.Collections.*;

public class CJKTokenizerPlugin extends Plugin implements AnalysisPlugin {
    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {
        return singletonMap("classic_cjk_tokenizer", ClassicCJKTokenizerFactory::new);
    }
}
