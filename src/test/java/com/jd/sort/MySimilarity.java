package com.jd.sort;

import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;

/**
 *
 * Indexing Time At indexing time, the indexer calls computeNorm(FieldInvertState), allowing the Similarity implementation to set a per-document value for the field that will be later accessible via LeafReader.getNormValues(String)
 *
 * Many formulas require the use of average document length, which can be computed via a combination of CollectionStatistics.sumTotalTermFreq() and CollectionStatistics.maxDoc() or CollectionStatistics.docCount(), depending upon whether the average should reflect field sparsity.
 *
 * Additional scoring factors can be stored in named NumericDocValuesFields and accessed at query-time with LeafReader.getNumericDocValues(String).
 *
 *
 *
 */
public class MySimilarity extends Similarity {

    /**
     * Indexing Time At indexing time, the indexer calls computeNorm(FieldInvertState), allowing the Similarity implementation to set a per-document value for the field that will be later accessible via LeafReader.getNormValues(String)
     * @param state
     * @return
     */
    @Override
    public long computeNorm(FieldInvertState state) {
        return 0;
    }

    @Override
    public SimWeight computeWeight(float boost, CollectionStatistics collectionStats, TermStatistics... termStats) {

        return null;
    }

    @Override
    public SimScorer simScorer(SimWeight weight, LeafReaderContext context) throws IOException {
        NumericDocValues numericDocValues = context.reader().getNumericDocValues("brandId");
        System.out.println("simScorer " + numericDocValues.docID());
        return new MyScorer(numericDocValues.longValue());
    }

    class MyScorer extends SimScorer{

        private long fieldValue;

        public MyScorer(long fieldValue){
            this.fieldValue = fieldValue;
        }

        @Override
        public float score(int doc, float freq) throws IOException {
            return fieldValue;
        }

        @Override
        public float computeSlopFactor(int distance) {
            return 0;
        }

        @Override
        public float computePayloadFactor(int doc, int start, int end, BytesRef payload) {
            return 0;
        }
    }
}
