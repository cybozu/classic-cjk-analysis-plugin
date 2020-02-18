package com.github.yokotaso.elasticsearch.plugin.analysis.classic.ckj;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cjk.ClassicCJKTokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenizerFactory;

class ClassicCJKTokenizerFactory extends AbstractTokenizerFactory {

    ClassicCJKTokenizerFactory(IndexSettings indexSettings, Environment environment, String name, Settings settings) {
        super( indexSettings, settings, name );
    }

    @Override
    public Tokenizer create() {
        return new ClassicCJKTokenizer();
    }
}
