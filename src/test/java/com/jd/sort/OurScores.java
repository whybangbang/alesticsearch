package com.jd.sort;

import com.jd.BaseOperation;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/search/similarities/TFIDFSimilarity.html
 * https://lucene.apache.org/core/7_5_0/core/org/apache/lucene/search/package-summary.html#scoring
 *
 */
public class OurScores extends BaseOperation {

    private Directory dir;
    @Before
    public void setUp() throws IOException {
        dir = FSDirectory.open(Paths.get("/Users/why/Desktop/lucene/baseDir"));
    }


    /**
     * DocValuesType
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void sortByModelId() throws IOException, ParseException {
        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);

            QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
            Query query = parser.parse("大众 OR 上汽");


//            query = new BoostQuery(query, 5);
            TopDocs topDocs = searcher.search(query, 100);

//            Sort sort = new Sort(new SortedNumericSortField("modelId", SortField.Type.LONG, true));
//            TopDocs topDocs = searcher.search(query, 100, sort);
            this.parseTopDocs(topDocs, searcher,  null,"id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }
    }

    // https://lucene.apache.org/core/7_5_0/core/org/apache/lucene/search/similarities/package-summary.html
    //
    @Test
    public void mySimilarityTest() throws IOException, ParseException {
        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);

            searcher.setSimilarity(new MySimilarity());

            QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
            Query query = parser.parse("大众 OR 上汽");
            TopDocs topDocs = searcher.search(query, 100);

            this.parseTopDocs(topDocs, searcher,  null,"id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }
    }


}
