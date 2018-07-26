package com.github.yokotaso.elasticsearch.plugin.classic.ckj.tokenizer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cjk.CJKTokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

class ClassicCJKTokenizerFactory extends AbstractTokenizerFactory {

    ClassicCJKTokenizerFactory(IndexSettings indexSettings, Environment environment, String s, Settings settings) {
        super(indexSettings, s, settings);
    }

    @Override
    public Tokenizer create() {
        return new CJKTokenizer();
    }
}
