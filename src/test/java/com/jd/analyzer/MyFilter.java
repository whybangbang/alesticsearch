package com.jd.analyzer;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeSource;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class MyFilter extends TokenFilter {

    Map<String, String> synonymMap;
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);
    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private AttributeSource.State current;
    private CharTermAttribute cta  = input.addAttribute(CharTermAttribute.class);

    private Stack<String> stack;

    /**
     * Construct a token stream filtering the given input.
     *
     * @param input
     */
    protected MyFilter(TokenStream input) {
        super(input);
    }


    public MyFilter(TokenStream input, Map<String, String> synonymMap){
        super(input);
        this.synonymMap = synonymMap;
        this.stack = new Stack<>();
    }


    /**
     * Consumers (i.e., {@link IndexWriter}) use this method to advance the stream to
     * the next token. Implementing classes must implement this method and update
     * the appropriate {@link AttributeImpl}s with the attributes of the next
     * token.
     * <P>
     * The producer must make no assumptions about the attributes after the method
     * has been returned: the caller may arbitrarily change it. If the producer
     * needs to preserve the state for subsequent calls, it can use
     * {@link #captureState} to create a copy of the current attribute state.
     * <p>
     * This method is called for every token of a document, so an efficient
     * implementation is crucial for good performance. To avoid calls to
     * {@link #addAttribute(Class)} and {@link #getAttribute(Class)},
     * references to all {@link AttributeImpl}s that this stream uses should be
     * retrieved during instantiation.
     * <p>
     * To ensure that filters and consumers know which attributes are available,
     * the attributes must be added during instantiation. Filters and consumers
     * are not required to check for availability of attributes in
     * {@link #incrementToken()}.
     *
     * @return false for end of stream; true otherwise
     */
    @Override
    public final boolean incrementToken() throws IOException {
        while (input.incrementToken()) {

            while (!stack.empty()){
                String ss = stack.pop();
                restoreState(current);
                termAtt.setEmpty();
                posIncrAtt.setPositionIncrement(0);
                termAtt.append(ss);
            }

            if(needSynonym(cta.toString())){
                current = captureState();
            }

            return true;
        }

        return false;
    }


    private boolean needSynonym(String token){
        String tmpSyno = synonymMap.get(token);
        if(tmpSyno != null){
            stack.push(tmpSyno);
            return true;
        }
        return false;
    }

}
