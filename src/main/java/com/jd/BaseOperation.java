package com.jd;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import java.io.IOException;

public class BaseOperation {


    protected void myReader(Directory dir, Query query) throws IOException, ParseException {

        try(IndexReader reader = DirectoryReader.open(dir)){
            IndexSearcher searcher = new IndexSearcher(reader);

            TopDocs topDocs = searcher.search(query, 100);

            this.parseTopDocs(topDocs, searcher, "id", "brandId", "modelName", "modelPrefix", "modelId", "brandName", "brandPrefix");


        }
    }


    protected void parseTopDocs(TopDocs topDocs, IndexSearcher indexSearcher, String... fields) throws IOException {
        ScoreDoc [] scoreDocs = topDocs.scoreDocs;
        for (int i = 0; i < scoreDocs.length; i++) {
            Document hitDoc = indexSearcher.doc(scoreDocs[i].doc);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[doc_id]: " + scoreDocs[i].doc + ", ");
            for(String field: fields){
                stringBuilder.append("[" + field + "]: " + hitDoc.get(field) + ", ");
            }

            System.out.println(stringBuilder.toString());
        }
    }
}
