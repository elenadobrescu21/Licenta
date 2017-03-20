package com.aii.platform.utils.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

public class LuceneReader {
	
	private IndexReader reader;
	private static final String INDEX_DIR="src/main/resources/static/file-storage/index-dir";
	
	public static final String CONTENTS="content";
	public static final String FILE_NAME="filename";
	public static final String FILE_PATH="filepath";
	public static final String FILE_ID="fileId";
	
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

	public IndexReader getReader() {
		return reader;
	}

	public void setReader(IndexReader reader) {
		this.reader = reader;
	}
	
	 
	 public int getDocumentId(String id) throws IOException {
		 IndexSearcher searcher = new IndexSearcher(reader);
		 TermQuery query = new TermQuery(new Term(FILE_ID, id));
		 TopDocs topdocs = searcher.search(query, 1);
		 int docId = topdocs.scoreDocs[0].doc;
		 return docId;
		 	 
	 }
	
	
	

}
