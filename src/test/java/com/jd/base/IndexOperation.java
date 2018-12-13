package com.jd.base;


import com.jd.BaseOperation;
import com.jd.LoadData;
import com.jd.sort.MySimilarity;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/codecs/lucene70/package-summary.html#package.description
 * https://segmentfault.com/q/1010000000585404
 *
 * lock没有删除的问题
 * 索引为String field query parse查不到的问题
 * LongPoint 数据为null的问题
 *
 *
 */
public class IndexOperation extends BaseOperation{

    private String indexFilePath;

    private Directory dir;

    private LoadData loadData;

    // writer相关

    @Before
    public void setUp() throws IOException {
        this.indexFilePath = "/Users/why/Desktop/lucene/baseDir";
        dir = FSDirectory.open(Paths.get(this.indexFilePath));

        loadData = new LoadData();
    }





    @Test
    public void write() {

        try {
            IndexWriter indexWriter = this.buildWriter2();
            List<Document> docs = loadData.readDocs3();
            indexWriter.addDocuments(docs);

            // commit 的作用
            indexWriter.close();
        } catch (IOException e) {
            System.out.print("xxx");
            e.printStackTrace();
        }

    }


    @Test
    public void merge() throws IOException {
        try(IndexWriter indexWriter = this.buildWriter();){
            indexWriter.forceMerge(1);
        }
    }

    @Test
    public void read() throws ParseException, IOException {
        QueryParser parser = new QueryParser("modelName", analyzer);
        Query query = parser.parse("高尔夫");
        this.myReader(dir, query);
    }

    @Test
    public void termRead() throws IOException{
        String testQueryString = "高";

        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);
            TermQuery query = new TermQuery(new Term("modelName", testQueryString));
            TopDocs topDocs = searcher.search(query, 10);
            this.parseTopDocs(topDocs, searcher, query,"id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }
    }


    private IndexWriter buildWriter() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    private IndexWriter buildWriter2() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        iwc.setSimilarity(new MySimilarity(new BM25Similarity()));
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

}
