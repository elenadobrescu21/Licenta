package com.aii.platform.utils.lucene;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;

@Service
public class Indexer {
	
	public static final String CONTENTS="content";
	public static final String FILE_NAME="filename";
	public static final String FILE_PATH="filepath";
	public static final String FILE_ID="fileId";
	
	public IndexWriter writer;
	
	 /* Indexed, tokenized, stored. */
    public static final FieldType TYPE_STORED = new FieldType();
	
	 static {
		 
	        TYPE_STORED.setIndexed(true);
	        TYPE_STORED.setTokenized(true);
	        TYPE_STORED.setStored(true);
	        TYPE_STORED.setStoreTermVectors(true);
	        TYPE_STORED.setStoreTermVectorPositions(true);
	        TYPE_STORED.freeze();
	    }
	 
	 public void createIndex(String indexDirectoryPath, File file, String content,int id) throws IOException {
			Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT,
	                analyzer);
	        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
	        
	        IndexWriter writer = new IndexWriter(indexDirectory, iwc);
	        addDocument(writer,file, content, id);
	        writer.close();
	}
	 
	public void deleteDocumentById(String documentId,String indexDirectoryPath) throws IOException {
		Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
	
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_CURRENT,
                analyzer);
        iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        
        IndexWriter writer = new IndexWriter(indexDirectory, iwc);
		this.writer.deleteDocuments(new Term(FILE_ID, documentId));
		writer.close();
	}
	 
	 public  void addDocument(IndexWriter writer, File file, String content, int id) throws IOException {
	        Document doc = new Document();
	        String fileId = Integer.toString(id);
	        Field fieldContent = new Field(CONTENTS, content, TYPE_STORED);
	        Field fileNameField = new Field(FILE_NAME, file.getName(),
	        		Field.Store.YES, Field.Index.NOT_ANALYZED);
	        System.out.println("File name din addDocument:" + file.getName());
	        Field filePathField = new Field(FILE_PATH, file.getCanonicalPath(),
					Field.Store.YES, Field.Index.NOT_ANALYZED);
	     Field documentId = new Field(FILE_ID, fileId, Field.Store.YES, Field.Index.NOT_ANALYZED);
	        System.out.println("File canonical Path din addDocument:" + file.getCanonicalPath());
	        doc.add(fieldContent);
	        doc.add(fileNameField);
	        doc.add(filePathField);
	        doc.add(documentId);
	        System.out.println("Indexing " + file.getCanonicalPath());
	        writer.addDocument(doc);
	             
	    }
	
	
}
