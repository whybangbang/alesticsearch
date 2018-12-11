package com.jd.query;

import com.jd.base.IndexOperation;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
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
 * https://lucene.apache.org/core/7_2_1/core/org/apache/lucene/search/package-summary.html#package.description
 * queryParser https://lucene.apache.org/core/7_2_1/queryparser/org/apache/lucene/queryparser/classic/package-summary.html#package.description
 */
public class AllKindsQuery extends IndexOperation {

   private Directory dir;
   @Before
   public void setUp() throws IOException {
      dir = FSDirectory.open(Paths.get("/Users/why/Desktop/lucene/baseDir"));
   }


   @Test
   public void termQueryTest() throws IOException, ParseException {
      TermQuery query = new TermQuery(new Term("modelName", "高"));
      myReader(dir, query);
   }

   @Test
   public void booleanQueryTest() throws IOException, ParseException {
      BooleanQuery.Builder builder = new BooleanQuery.Builder();
      builder.add(new TermQuery(new Term("modelName", "高")), BooleanClause.Occur.MUST);
      // 这个就不行因为QueryParser默认是 or关系
//      builder.add(new QueryParser("brandName", new StandardAnalyzer()).parse("一汽大众"), BooleanClause.Occur.MUST_NOT);
      builder.add(new QueryParser("brandName", new StandardAnalyzer()).parse("一"), BooleanClause.Occur.MUST_NOT);
      myReader(dir, builder.build());
   }

   @Test
   public void phraseQueryTest() throws IOException, ParseException {
      PhraseQuery query = new PhraseQuery("modelName", "高尔");
      myReader(dir, query);
   }

   @Test
   public void queryParserTest() throws ParseException, IOException {
      QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
      Query query = parser.parse("一汽大众");
      myReader(dir, query);
   }

   public void queryParserTest2() throws IOException, ParseException {
      QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
      Query query = parser.parse("一汽大众");
      myReader(dir, query);
   }


}
