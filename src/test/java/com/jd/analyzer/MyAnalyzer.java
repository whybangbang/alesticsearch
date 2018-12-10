package com.jd.analyzer;

import org.apache.lucene.analysis.Analyzer;

public class MyAnalyzer extends Analyzer {
    @Override
    protected TokenStreamComponents createComponents(String fieldName) {
        return null;
    }
}
