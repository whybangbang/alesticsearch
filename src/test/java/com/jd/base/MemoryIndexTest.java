package com.jd.base;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.memory.MemoryIndex;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.junit.Test;

public class MemoryIndexTest {

    @Test
    public void memoryTest() throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        MemoryIndex index = new MemoryIndex();
        index.addField("content", "Readings about Salmons and other select Alaska fishing Manuals", analyzer);
        index.addField("author", "Tales of James", analyzer);
        QueryParser parser = new QueryParser("content", analyzer);
        float score = index.search(parser.parse("why"));
        if ( score > 0.0){
            System.out.println("it's a match");
        } else{
            System.out.println("no match found");
        }
        System.out.println("indexData=" + index.toString());
    }
}
