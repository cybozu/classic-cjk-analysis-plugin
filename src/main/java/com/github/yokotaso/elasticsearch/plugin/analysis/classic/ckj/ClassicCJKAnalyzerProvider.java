package com.github.yokotaso.elasticsearch.plugin.analysis.classic.ckj;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.cjk.ClassicCJKAnalyzer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.analysis.Analysis;

class ClassicCJKAnalyzerProvider extends AbstractIndexAnalyzerProvider<ClassicCJKAnalyzer> {

    private final ClassicCJKAnalyzer analyzer;
    /**
     * Constructs a new analyzer component, with the index name and its settings and the analyzer name.
     *
     * @param indexSettings the settings and the name of the index
     * @param name          The analyzer name
     * @param settings
     */
    public ClassicCJKAnalyzerProvider(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);
        CharArraySet stopWords = Analysis.parseStopWords(
                env, indexSettings.getIndexVersionCreated(), settings, ClassicCJKAnalyzer.getDefaultStopSet());

        analyzer = new ClassicCJKAnalyzer(stopWords);
        analyzer.setVersion(version);
    }

    @Override
    public ClassicCJKAnalyzer get() {
        return analyzer;
    }
}
