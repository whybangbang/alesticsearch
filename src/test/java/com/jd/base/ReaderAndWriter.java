package com.jd.base;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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

public class ReaderAndWriter {

    private String indexFilePath;
    private Analyzer analyzer;
    private Directory dir;

    // writer相关

    // reader相关
    private String defaultField;

    @Before
    public void setUp() throws IOException {
        this.indexFilePath = "/Users/why/Desktop/lucene/baseDir";
        analyzer = new StandardAnalyzer();
        dir = FSDirectory.open(Paths.get(this.indexFilePath));

        this.defaultField = "title";
    }

    @Test
    public void write() throws IOException {
        IndexWriter indexWriter = this.buildWriter();


    }

    /**
     *
     * searcher 调用链
     * {@link IndexSearcher#search(Query, int)}
     * {@link IndexSearcher#searchAfter(ScoreDoc, Query, int)}
     * {@link IndexSearcher#search(Query, CollectorManager)}
     * {@link IndexSearcher#search(Query, CollectorManager)}
     * {@link IndexSearcher#search(List, Weight, Collector)}
     *
     *
     * @throws ParseException
     */
    @Test
    public void read() throws ParseException, IOException {
        String testQueryString = "hello world";

        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser(this.defaultField, analyzer);
            Query query = parser.parse(testQueryString);
            TopDocs topDocs = searcher.search(query, 5);

        }


    }

    /**
     * {@link IndexWriterConfig}
     * {@link Analyzer}
     * {@link Directory}
     * {@link }
     * {@link FSDirectory} three class implements,  {@link org.apache.lucene.store.SimpleFSDirectory}, {@link org.apache.lucene.store.MMapDirectory}, {@link org.apache.lucene.store.NIOFSDirectory}
     * {@link FSDirectory#open} auto choose best fsdDirectory
     *
     *
     * {@link org.apache.lucene.index.LiveIndexWriterConfig}
     * merge相关
     * {@link org.apache.lucene.index.MergeScheduler}
     * {@link org.apache.lucene.index.MergePolicy}
     *
     * flush
     * {@link org.apache.lucene.index.FlushPolicy}
     * {@link org.apache.lucene.index.LiveIndexWriterConfig#setRAMBufferSizeMB}
     * {@link org.apache.lucene.index.LiveIndexWriterConfig#setMaxBufferedDocs}
     * {@link IndexWriterConfig#DEFAULT_RAM_BUFFER_SIZE_MB}
     *
     * @return
     */
    private IndexWriter buildWriter() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

}
