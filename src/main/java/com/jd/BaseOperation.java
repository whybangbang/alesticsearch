package com.jd;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;

public class BaseOperation {

    protected Analyzer analyzer;

    {
        analyzer = new StandardAnalyzer();

        analyzer = new IKAnalyzer(true);
    }


    protected void myReader(Directory dir, Query query) throws IOException, ParseException {

        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);
            TopDocs topDocs = searcher.search(query, 100);

            this.parseTopDocs(topDocs, searcher,  query,"id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }
    }


    protected void parseTopDocs(TopDocs topDocs, IndexSearcher indexSearcher, Query query, String... fields) throws IOException {
        ScoreDoc [] scoreDocs = topDocs.scoreDocs;

        for (int i = 0; i < scoreDocs.length; i++) {



            Document hitDoc = indexSearcher.doc(scoreDocs[i].doc);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[doc_id]: " + scoreDocs[i].doc + ", ");
            stringBuilder.append("[score]: " + scoreDocs[i].score + ", ");
            for(String field: fields){
                stringBuilder.append("[" + field + "]: " + hitDoc.get(field) + ", ");
            }

            if(query != null){
                Explanation explanation =  indexSearcher.explain(query, scoreDocs[i].doc);
                stringBuilder.append("[explain]: " + explanation.toString());
            }

            System.out.println(stringBuilder.toString());
        }
    }
}
