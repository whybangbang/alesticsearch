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
//       PhraseQuery.Builder builder = new PhraseQuery.Builder();
//       builder.add(new Term("modelName", "高"), 0);
//       builder.add(new Term("modelName", "尔"), 1);
//       PhraseQuery pq = builder.build();


       PhraseQuery phraseQuery = new PhraseQuery("modelName", "高", "尔");
//       PhraseQuery phraseQuery = new PhraseQuery("modelName", "高尔");
       myReader(dir, phraseQuery);
   }

    /**
     * 这个不仅排除掉了"上汽"也排除掉了"一汽"
     * @throws ParseException
     * @throws IOException
     */
   @Test
   public void queryParserTest() throws ParseException, IOException {
      QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
      Query query = parser.parse("大众 NOT 上汽");
      myReader(dir, query);
   }

    /**
     * 这个一汽就出现了
     * @throws IOException
     * @throws ParseException
     */
   @Test
   public void queryParserTest2() throws IOException, ParseException {
      QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
      Query query = parser.parse("大众 NOT \"上汽\"");
      myReader(dir, query);
   }

    /**
     * 这样也可以只排除掉"上汽",留下"一汽",但竟然还有众泰
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void queryParserTest3() throws IOException, ParseException {
        QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
        Query query = parser.parse("大众 NOT 上");
        myReader(dir, query);
    }

    /**
     * 这样就排除掉了众泰,但这种操作利用的是phrase, 麻烦而且好性能
     * 解决办法还是用空间换时间，提前处理，引入中文分词器
     * @throws IOException
     * @throws ParseException
     */
    @Test
    public void queryParserTest4() throws IOException, ParseException {
        QueryParser parser = new QueryParser("brandName", new StandardAnalyzer());
        Query query = parser.parse("\"大众\" NOT 上");
        myReader(dir, query);
    }


}
