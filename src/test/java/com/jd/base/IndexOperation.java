package com.jd.base;


import com.jd.BaseOperation;
import com.jd.LoadData;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/codecs/lucene70/package-summary.html#package.description
 */
public class IndexOperation extends BaseOperation{

    private String indexFilePath;
    private Analyzer analyzer;
    private Directory dir;

    private LoadData loadData;

    // writer相关

    // reader相关
    private String defaultField;

    @Before
    public void setUp() throws IOException {
        this.indexFilePath = "/Users/why/Desktop/lucene/baseDir";
        analyzer = new StandardAnalyzer();
        dir = FSDirectory.open(Paths.get(this.indexFilePath));

        this.defaultField = "modelName";

        loadData = new LoadData();
    }





    @Test
    public void write() throws IOException {
        try(IndexWriter indexWriter = this.buildWriter();){
            List<Document> docs = loadData.readDocs();
            indexWriter.addDocuments(docs);
            indexWriter.commit();
            indexWriter.flush();
        }


    }


    @Test
    public void merge() throws IOException {
        try(IndexWriter indexWriter = this.buildWriter();){
            indexWriter.forceMerge(1);
            indexWriter.close();
        }
    }

    @Test
    public void read() throws ParseException, IOException {
        String testQueryString = "现代";

        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser(this.defaultField, analyzer);
            Query query = parser.parse(testQueryString);
            TopDocs topDocs = searcher.search(query, 5);

            this.parseTopDocs(topDocs, searcher, "id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }


    }


    private IndexWriter buildWriter() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

}