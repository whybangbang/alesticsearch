package com.jd.analyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        //改完用luka看看
        //更改了词库再看看效果
//        analyzer = new IKAnalyzer();

//        analyzer = new EnglishAnalyzer();

        String text = "电子产品";
        text = "i wish to buy a iphone";
        this.analysis(analyzer, text);


    }

    @Test
    public void customAnalysis() throws IOException {
        Set stopWords = new HashSet();
        //不加和加显示
        stopWords.add("产品");

        Map synonymWords = new HashMap();
        synonymWords.put("电子", "electronic");
        analyzer = new MyAnalyzer(stopWords, synonymWords);

        String text = "电子产品系列two";
        this.analysis(analyzer, text);
    }

    @Test
    public void analysisPos() throws IOException {
        analyzer = new StandardAnalyzer();
        this.analysis(analyzer, "stuff no and synonym phrase");
    }

    public void analysis(Analyzer analyzer, String text) throws IOException {
        TokenStream ts = analyzer.tokenStream(DEFAULT_FILED, text);
        OffsetAttribute offsetAtt = ts.addAttribute(OffsetAttribute.class);
        CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);

        PositionIncrementAttribute positionIncrementAttribute = ts.addAttribute(PositionIncrementAttribute.class);

        try {
            ts.reset(); // Resets this stream to the beginning. (Required)
            while (ts.incrementToken()) {
                // Use AttributeSource.reflectAsString(boolean)
                // for token stream debugging.
//                System.out.println("token: " + ts.reflectAsString(true));
                System.out.println("token: " + charTermAttribute.toString());


                int currPosition = positionIncrementAttribute.getPositionIncrement();
                positionIncrementAttribute.setPositionIncrement(2);
                System.out.println("position: " + currPosition);


                System.out.println("token start offset: " + offsetAtt.startOffset());
                System.out.println("token end offset: " + offsetAtt.endOffset());
                System.out.println("------------------");
            }
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
        } finally {
            ts.close(); // Release resources associated with this stream.
        }
    }
}
