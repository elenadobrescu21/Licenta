package com.aii.platform.utils.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.BytesRef;

public class CosineDocumentSimilarity {
	
	public static final String CONTENT = "content";

    private Set<String> terms = new HashSet<String>();
    private  RealVector v1;
    private  RealVector v2;
    
    public CosineDocumentSimilarity(IndexReader reader, int firstId, int secondId) throws IOException {
        
        Map<String, Integer> f1 = getTermFrequencies(reader, firstId);
        Map<String, Integer> f2 = getTermFrequencies(reader, secondId);
        reader.close();
        v1 = toRealVector(f1);
        v2 = toRealVector(f2);
    }
    
   public double getCosineSimilarity() {
        return (v1.dotProduct(v2)) / (v1.getNorm() * v2.getNorm());
    }
   

   public Map<String, Integer> getTermFrequencies(IndexReader reader, int docId)
           throws IOException {
       Terms vector = reader.getTermVector(docId, CONTENT);
       TermsEnum termsEnum = null;
       termsEnum = vector.iterator(termsEnum);
       Map<String, Integer> frequencies = new HashMap<String, Integer>();
       BytesRef text = null;
       while ((text = termsEnum.next()) != null) {
           String term = text.utf8ToString();
           int freq = (int) termsEnum.totalTermFreq();
           frequencies.put(term, freq);
           //System.out.println(term + ":" + freq);
           terms.add(term);
       }
       return frequencies;
   }
   
   RealVector toRealVector(Map<String, Integer> map) {
       RealVector vector = new ArrayRealVector(terms.size());
       int i = 0;
       for (String term : terms) {
           int value = map.containsKey(term) ? map.get(term) : 0;
           vector.setEntry(i++, value);
       }
       return (RealVector) vector.mapDivide(vector.getL1Norm());
   }
   
}
