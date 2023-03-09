package org.apache.lucene.analysis.cjk;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.tests.analysis.BaseTokenStreamTestCase;
import org.assertj.core.util.Lists;
import org.junit.Test;

public class TestClassicCJKTokenizerSpec extends BaseTokenStreamTestCase {
    class TestToken {
        String termText;
        int start;
        int end;
        String type;
    }

    public TestToken newToken(String termText, int start, int end, int type) {
        TestToken token = new TestToken();
        token.termText = termText;
        token.type = ClassicCJKTokenizer.TOKEN_TYPE_NAMES[type];
        token.start = start;
        token.end = end;
        return token;
    }

    public void checkCJKToken(final String str, final TestToken[] out_tokens) throws IOException {
        Analyzer analyzer = new ClassicCJKAnalyzer( ClassicCJKAnalyzer.getDefaultStopSet() );
        String terms[] = new String[out_tokens.length];
        int startOffsets[] = new int[out_tokens.length];
        int endOffsets[] = new int[out_tokens.length];
        String types[] = new String[out_tokens.length];
        for ( int i = 0; i < out_tokens.length; i++ ) {
            terms[i] = out_tokens[i].termText;
            startOffsets[i] = out_tokens[i].start;
            endOffsets[i] = out_tokens[i].end;
            types[i] = out_tokens[i].type;
        }
        assertAnalyzesTo( analyzer, str, terms, startOffsets, endOffsets, types, null );
    }

    public void checkCJKTokenReusable(final Analyzer a, final String str, final TestToken[] out_tokens)
        throws IOException {
        Analyzer analyzer = new ClassicCJKAnalyzer( CharArraySet.EMPTY_SET );
        String terms[] = new String[out_tokens.length];
        int startOffsets[] = new int[out_tokens.length];
        int endOffsets[] = new int[out_tokens.length];
        String types[] = new String[out_tokens.length];
        for ( int i = 0; i < out_tokens.length; i++ ) {
            terms[i] = out_tokens[i].termText;
            startOffsets[i] = out_tokens[i].start;
            endOffsets[i] = out_tokens[i].end;
            types[i] = out_tokens[i].type;
        }
        assertAnalyzesTo( analyzer, str, terms, startOffsets, endOffsets, types, null );
    }

    @Test
    public void 英数字の分割ルール() throws Exception {
        // 連続する英数字は分割されない
        checkCJKToken(
            "test123",
            new TestToken[] { newToken( "test123", 0, 7, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
        // 英数字は空白で分割
        checkCJKToken(
            "test 123",
            new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "123", 5, 8, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
    }

    @Test
    public void 英字は小文字に変換される() throws Exception {
        // lowerCaseに変換される
        checkCJKToken( "TEST", new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
    }

    @Test
    public void 半角英数と全角は分割される() throws Exception {
        // 半角英数と全角は分割される
        checkCJKToken(
            "testあい",
            new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "あい", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );
        checkCJKToken(
            "あいtest",
            new TestToken[] { newToken( "あい", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "test", 2, 6, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );

        checkCJKToken(
            "java ｃ１ｃ２ｃ３",
            new TestToken[] { newToken( "java", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "c1c2c3", 5, 11, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
    }

    @Test
    public void 記号は基本的にトークナイズされない() throws Exception {
        for ( String symbol : Lists.newArrayList( ",", "-", "(", ")", "*", "$" ) ) {
            checkCJKToken( symbol, new TestToken[] {} );
        }

    }

    @Test
    public void プラス_アンダースコア_ハッシュは記号で特別扱い() throws Exception {
        // # _ , + はトークナイズされる
        checkCJKToken(
            "＃ハッシュ",
            new TestToken[] { newToken( "#", 0, 1, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "ハッ", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ッシ", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "シュ", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ), } );

        checkCJKToken(
            "＋プラス",
            new TestToken[] { newToken( "+", 0, 1, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "プラ", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ラス", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );

        checkCJKToken(
            "＿アンダ",
            new TestToken[] { newToken( "_", 0, 1, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "アン", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ンダ", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );

        // #, _, + と半角英数は分割されない
        checkCJKToken( "#hash", new TestToken[] { newToken( "#hash", 0, 5, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );

        checkCJKToken( "+plus", new TestToken[] { newToken( "+plus", 0, 5, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );

        checkCJKToken(
            "_underscore_",
            new TestToken[] { newToken( "_underscore_", 0, 12, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
    }

    @Test
    public void CJK文字はバイグラム分割() throws Exception {
        // CJK文字はバイグラム分割
        checkCJKToken(
            "トークナイズ仕様テスト",
            new TestToken[] { newToken( "トー", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ーク", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "クナ", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ナイ", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "イズ", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "ズ仕", 5, 7, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "仕様", 6, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "様テ", 7, 9, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "テス", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "スト", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );
    }

    @Test
    public void testStopWords() throws IOException {
        var stopWords = "a\n" + "and\n" + "are\n" + "as\n" + "at\n" + "be\n" + "but\n" + "by\n" + "for\n" + "if\n"
            + "in\n" + "into\n" + "is\n" + "it\n" + "no\n" + "not\n" + "of\n" + "on\n" + "or\n" + "s\n" + "such\n"
            + "t\n" + "that\n" + "the\n" + "their\n" + "then\n" + "there\n" + "these\n" + "they\n" + "this\n" + "to\n"
            + "was\n" + "will\n" + "with\n" + "www";
        checkCJKToken( stopWords, new TestToken[] {} );
    }
}
