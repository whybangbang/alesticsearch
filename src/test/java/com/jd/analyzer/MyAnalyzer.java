package com.jd.analyzer;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.PackedTokenAttributeImpl;
import org.apache.lucene.util.AttributeFactory;
import org.wltea.analyzer.lucene.IKTokenizer;

import java.util.*;

public class MyAnalyzer extends Analyzer {

    private Set<String> stopwords;
    private Map synonymwords;


    public MyAnalyzer(Set<String> stopwords, Map synonymwords){
        this.stopwords = stopwords;
        this.synonymwords = synonymwords;
    }

    @Override
    protected TokenStreamComponents createComponents(
            String fieldName) {

        Tokenizer _IKTokenizer = new IKTokenizer(AttributeFactory.getStaticImplementation(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY, PackedTokenAttributeImpl.class));
        TokenFilter stopTokenFilter = new StopFilter(_IKTokenizer, new CharArraySet(stopwords, true));


//        SynonymMap.Builder builder = new SynonymMap.Builder(true);
//        builder.add(new CharsRef("two"), new CharsRef("color"), true);
//
//        SynonymMap synonymMap;
//        try {
//            synonymMap = builder.build();
//            TokenFilter synonymTokenFIlter =  new SynonymGraphFilter(stopTokenFilter, synonymMap, true);
//            return new TokenStreamComponents(_IKTokenizer, synonymTokenFIlter);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        TokenFilter myFilter = new MyFilter(stopTokenFilter, synonymwords);

        return  new TokenStreamComponents(_IKTokenizer, myFilter);
    }
}
