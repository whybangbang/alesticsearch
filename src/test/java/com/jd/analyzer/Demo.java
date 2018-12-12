package com.jd.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.junit.Test;

import java.io.IOException;

/**
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/analysis/package-summary.html#package.description
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/analysis/TokenStream.html
 *

 *
 */
public class Demo {

    String DEFAULT_FILED = "myField";
    Analyzer analyzer;


    @Test
    public void analyzeTest() throws IOException {
        analyzer = new StandardAnalyzer();
        String text = "高尔夫";
        this.analysis(analyzer, text);
    }

    public void analysis(Analyzer analyzer, String text) throws IOException {
        TokenStream ts = analyzer.tokenStream(DEFAULT_FILED, text);
        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);

        try {
            ts.reset(); // Resets this stream to the beginning. (Required)
            while (ts.incrementToken()) {
                // Use AttributeSource.reflectAsString(boolean)
                // for token stream debugging.
//                System.out.println("token: " + ts.reflectAsString(true));
                System.out.println("token: " + charTermAttribute.toString());
                System.out.println("token start offset: " + offsetAtt.startOffset());
                System.out.println("  token end offset: " + offsetAtt.endOffset());
            }
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
        } finally {
            ts.close(); // Release resources associated with this stream.
        }
    }
}
