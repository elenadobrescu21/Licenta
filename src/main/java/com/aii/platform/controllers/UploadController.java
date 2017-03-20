package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.TermVector;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.utils.FileUtils;
import com.aii.platform.utils.PDFBoxUtils;
import com.aii.platform.utils.lucene.CosineDocumentSimilarity;
import com.aii.platform.utils.lucene.Indexer;
import com.aii.platform.utils.lucene.LuceneReader;

@Controller
public class UploadController {
	
	private static final String INDEX_DIR_PATH = "src/main/resources/static/file-storage/index-dir";
	private static final String UPLOAD_DIR = "src/main/resources/static/file-storage/uploads";
	private static final String TEMP_DIR = "src/main/resources/static/file-storage/recents";
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	FileUtils fileUtils;
	
	@Autowired
	Indexer indexer;
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadArticle(@RequestParam(value="title") String title,
			@RequestParam(value = "file") MultipartFile file,HttpServletRequest request) throws IOException {
		
		String token = request.getHeader("X-Auth-Token");
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserRepository.findByUsername(username);
		String titleToBeCompared = title.replace("\"", "");
		String titleToBeSaved = title.replace("\"", "");
		
		String filename = file.getOriginalFilename();
		String filepath = Paths.get(TEMP_DIR, filename).toString();
		
		try {
			fileUtils.saveFileLocally(filepath, file);
			System.out.println("File uploaded on folder recents");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		PDFBoxUtils pdfUtils = new PDFBoxUtils();
	    String content = pdfUtils.getTextContentFromPDF(filepath);
	    System.out.println(content);
	    
	    String maxID = uploadedArticleRepository.getMaxId();
	    int maxId = (maxID==null) ? 0 : Integer.parseInt(maxID);
	    int currentId;
	    if(maxId == 0) {
	    	currentId = 0;
	    } else {
	    	currentId = maxId + 1;
	    }
	    String id = Integer.toString(currentId);
	    
	    File fileToUpload = fileUtils.multipartToFile(file);
	    Indexer indexer = new Indexer();
	    indexer.createIndex(INDEX_DIR_PATH, fileToUpload, content, currentId);
	    
	    LuceneReader reader = new LuceneReader();
	  	System.out.println("Numarul de fisiere indexate:" + reader.countNumberOfIndexedDocuments());
	  	int luceneDocumentID = reader.getDocumentId(id);
	  	System.out.println("Document ID:"+ reader.getDocumentId(id));
	  	
	  	boolean isSimilar = false;
	  	int idSimilar;
	  	for(int i=0; i<reader.countNumberOfIndexedDocuments()-2; i++) {
	  		LuceneReader reader2 = new LuceneReader();

	  		CosineDocumentSimilarity cosSim = new CosineDocumentSimilarity(reader2.getReader(),
	  				luceneDocumentID, i);
	  		double cosineSimilarity = cosSim.getCosineSimilarity();
	  		if(cosineSimilarity > 0.95) {
	  			isSimilar = true;
	  			idSimilar = i;
	  			LuceneReader reader3 = new LuceneReader();
	  			Document similarDocument = reader3.getReader().document(idSimilar);
	  	        System.out.println("Filepath of similar document " + similarDocument.get("filepath"));
	  			
	  		}
	
	  		System.out.println("Similaritatea dintre document si documentul cu id-ul " + i + "este "
	  				+ cosineSimilarity);

	  	}
	  	//daca este similar atunci sterge documentul din folder si recreeaza indexul cu restul documentelor
	  	if(isSimilar == true) {
	  		return new ResponseEntity<Response>(new Response("Exista document similar"), new HttpHeaders(), HttpStatus.OK);
	  	}else {
	  		String filepathToUploads = Paths.get(UPLOAD_DIR, filename).toString();
	  		
	  		//fileUtils.saveFileLocally(filepathToUploads, file);
	  		//fileUtils.deleteFileFromDirectory(filepath);
	  	 
		    UploadedArticle articleToUpload = new UploadedArticle(titleToBeSaved,filename);
		    user.getUploadedArticles().add(articleToUpload);
		    articleToUpload.setAppUser(user);
		    try {
		      appUserRepository.save(user);	      
		      uploadedArticleRepository.save(articleToUpload);
		    } catch (Exception e) {
		    	  e.printStackTrace();
		    	  System.out.println("Couldn't upload file to db");
		    	  return new ResponseEntity<Response>(new Response("Couldn't upload file to database"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
		      }
	  	}
	    
	  	return new ResponseEntity<>(reader.getDocumentId(id), new HttpHeaders(), HttpStatus.OK);
	  	
	 
		} // method uploadFile

}
