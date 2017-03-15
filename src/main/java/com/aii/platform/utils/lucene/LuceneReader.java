package com.aii.platform.utils.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

public class LuceneReader {
	
	IndexReader reader;
	private static final String INDEX_DIR="src/main/resources/static/file-storage/index-dir";
	
	public LuceneReader() {
		try {
			reader = IndexReader.open(FSDirectory.open(new File(INDEX_DIR)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int countNumberOfIndexedDocuments(){
		return reader.numDocs();
	}
	

}
