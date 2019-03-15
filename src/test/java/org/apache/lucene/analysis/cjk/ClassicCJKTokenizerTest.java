package org.apache.lucene.analysis.cjk;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.analysis.CharArraySet;
import org.junit.Test;

/**
 * <a href=
 * "https://github.com/apache/lucene-solr/blob/releases/lucene-solr/4.7.2/lucene/analysis/common/src/test/org/apache/lucene/analysis/cjk/TestCJKTokenizer.java">Copy
 * From TestCJKTokenizer</a>
 */
public class ClassicCJKTokenizerTest extends BaseTokenStreamTestCase {

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
    public void testJa1() throws IOException {
        String str = "\u4e00\u4e8c\u4e09\u56db\u4e94\u516d\u4e03\u516b\u4e5d\u5341";

        TestToken[] out_tokens = { newToken( "\u4e00\u4e8c", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e8c\u4e09", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e09\u56db", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u56db\u4e94", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e94\u516d", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u516d\u4e03", 5, 7, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e03\u516b", 6, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u516b\u4e5d", 7, 9, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e5d\u5341", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testJa2() throws IOException {
        String str = "\u4e00 \u4e8c\u4e09\u56db \u4e94\u516d\u4e03\u516b\u4e5d \u5341";

        TestToken[] out_tokens = { newToken( "\u4e00", 0, 1, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e8c\u4e09", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e09\u56db", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e94\u516d", 6, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u516d\u4e03", 7, 9, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u4e03\u516b", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u516b\u4e5d", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u5341", 12, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testC() throws IOException {
        String str = "abc defgh ijklmn opqrstu vwxy z";

        TestToken[] out_tokens = { newToken( "abc", 0, 3, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "defgh", 4, 9, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "ijklmn", 10, 16, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "opqrstu", 17, 24, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "vwxy", 25, 29, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "z", 30, 31, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ), };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testMix() throws IOException {
        String str = "\u3042\u3044\u3046\u3048\u304aabc\u304b\u304d\u304f\u3051\u3053";

        TestToken[] out_tokens = { newToken( "\u3042\u3044", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3044\u3046", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3046\u3048", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3048\u304a", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "abc", 5, 8, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u304b\u304d", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304d\u304f", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304f\u3051", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3051\u3053", 11, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testMix2() throws IOException {
        String str = "\u3042\u3044\u3046\u3048\u304aab\u3093c\u304b\u304d\u304f\u3051 \u3053";

        TestToken[] out_tokens = { newToken( "\u3042\u3044", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3044\u3046", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3046\u3048", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3048\u304a", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "ab", 5, 7, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u3093", 7, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "c", 8, 9, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u304b\u304d", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304d\u304f", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304f\u3051", 11, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3053", 14, 15, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testSingleChar() throws IOException {
        String str = "\u4e00";

        TestToken[] out_tokens = { newToken( "\u4e00", 0, 1, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ), };
        checkCJKToken( str, out_tokens );
    }

    /*
     * Full-width text is normalized to half-width
     */
    @Test
    public void testFullWidth() throws Exception {
        String str = "Ｔｅｓｔ １２３４";
        TestToken[] out_tokens = { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "1234", 5, 9, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    /*
     * Non-english text (not just CJK) is treated the same as CJK: C1C2 C2C3
     */
    @Test
    public void testNonIdeographic() throws Exception {
        String str = "\u4e00 روبرت موير";
        TestToken[] out_tokens = { newToken( "\u4e00", 0, 1, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "رو", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "وب", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "بر", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "رت", 5, 7, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "مو", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "وي", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "ير", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    /*
     * Non-english text with nonletters (non-spacing marks,etc) is treated as C1C2 C2C3, except for words are split
     * around non-letters.
     */
    @Test
    public void testNonIdeographicNonLetter() throws Exception {
        String str = "\u4e00 رُوبرت موير";
        TestToken[] out_tokens = { newToken( "\u4e00", 0, 1, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "ر", 2, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "وب", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "بر", 5, 7, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "رت", 6, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "مو", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "وي", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "ير", 11, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKToken( str, out_tokens );
    }

    @Test
    public void testTokenStream() throws Exception {
        Analyzer analyzer = new CJKAnalyzer();
        assertAnalyzesTo( analyzer, "\u4e00\u4e01\u4e02", new String[] { "\u4e00\u4e01", "\u4e01\u4e02" } );
    }

    @Test
    public void testReusableTokenStream() throws Exception {
        Analyzer analyzer = new CJKAnalyzer();
        String str = "\u3042\u3044\u3046\u3048\u304aabc\u304b\u304d\u304f\u3051\u3053";

        TestToken[] out_tokens = { newToken( "\u3042\u3044", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3044\u3046", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3046\u3048", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3048\u304a", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "abc", 5, 8, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u304b\u304d", 8, 10, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304d\u304f", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304f\u3051", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3051\u3053", 11, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKTokenReusable( analyzer, str, out_tokens );

        str = "\u3042\u3044\u3046\u3048\u304aab\u3093c\u304b\u304d\u304f\u3051 \u3053";
        TestToken[] out_tokens2 = { newToken( "\u3042\u3044", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3044\u3046", 1, 3, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3046\u3048", 2, 4, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3048\u304a", 3, 5, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "ab", 5, 7, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u3093", 7, 8, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "c", 8, 9, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
            newToken( "\u304b\u304d", 9, 11, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304d\u304f", 10, 12, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u304f\u3051", 11, 13, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
            newToken( "\u3053", 14, 15, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) };
        checkCJKTokenReusable( analyzer, str, out_tokens2 );
    }

    /**
     * LUCENE-2207: wrong offset calculated by end()
     */
    @Test
    public void testFinalOffset() throws IOException {
        checkCJKToken( "あい", new TestToken[] { newToken( "あい", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );
        checkCJKToken( "あい   ", new TestToken[] { newToken( "あい", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );
        checkCJKToken( "test", new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
        checkCJKToken( "test   ", new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
        checkCJKToken(
            "あいtest",
            new TestToken[] { newToken( "あい", 0, 2, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ),
                newToken( "test", 2, 6, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
        checkCJKToken(
            "testあい    ",
            new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "あい", 4, 6, ClassicCJKTokenizer.DOUBLE_TOKEN_TYPE ) } );
    }

    /** blast some random strings through the analyzer */
    @Test
    public void testRandomStrings() throws Exception {
        checkRandomData( random(), new CJKAnalyzer(), 10000 * RANDOM_MULTIPLIER );
    }

    @Test
    public void トークナイズ仕様テスト() throws Exception {
        // 連続する英数字は分割されない
        checkCJKToken(
            "test123",
            new TestToken[] { newToken( "test123", 0, 7, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );
        // 英数字は空白で分割
        checkCJKToken(
            "test 123",
            new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ),
                newToken( "123", 5, 8, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );

        // lowerCaseに変換される
        checkCJKToken( "TEST", new TestToken[] { newToken( "test", 0, 4, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE ) } );

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
        checkCJKToken(
                "#hash",
                new TestToken[] { newToken( "#hash", 0, 5, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE )} );

        checkCJKToken(
                "+plus",
                new TestToken[] { newToken( "+plus", 0, 5, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE )} );

        checkCJKToken(
                "_underscore_",
                new TestToken[] { newToken( "_underscore_", 0, 12, ClassicCJKTokenizer.SINGLE_TOKEN_TYPE )} );



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
}
