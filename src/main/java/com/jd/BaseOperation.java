package com.jd;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class BaseOperation {


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
