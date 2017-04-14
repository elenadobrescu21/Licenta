package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Response;
import com.aii.platform.models.Tag;
import com.aii.platform.models.Coauthor;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.TagRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.utils.FileUtils;
import com.aii.platform.utils.PDFBoxUtils;
import com.aii.platform.utils.lucene.CosineDocumentSimilarity;
import com.aii.platform.utils.lucene.Indexer;
import com.aii.platform.utils.lucene.LuceneReader;
import com.google.gson.Gson;

@Controller
public class UploadController {
	
	private static final String INDEX_DIR_PATH = "src/main/resources/static/file-storage/index-dir";
	private static final String UPLOAD_DIR = "src/main/resources/static/file-storage/uploads";
	private static final String TEMP_DIR = "src/main/resources/static/file-storage/recents";
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	@Autowired TagRepository tagRepository;
	
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private Indexer indexer;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@Autowired 
	private AppUserService appUserService;
	
	@Autowired
	private TagService tagService;
	
	public Coauthor[] getCoauthors(HttpServletRequest request) {
		String coauthors = request.getParameter("coauthors");
		Coauthor[] coauthorsArray = null;
		
		System.out.println(coauthors.length());
		if(coauthors!=null && coauthors.length() > 2) {
			coauthorsArray = new Gson().fromJson(coauthors, Coauthor[].class);
		}
		return coauthorsArray;
	}
	
	public String[] getTags(HttpServletRequest request) {
		String tags = request.getParameter("tags");
		String[] userTags = null;
		if(tags!=null && tags.length() > 2) {
			userTags =  new Gson().fromJson(tags, String[].class);	
		}
		for(String userTag: userTags) {
			userTag = userTag.toLowerCase();
		}
		return userTags;
	}
	
	public void indexDocument(MultipartFile file, String content, int currentId) throws IllegalStateException, IOException {
		 File fileToUpload = fileUtils.multipartToFile(file);
		  Indexer indexer = new Indexer();
		  indexer.createIndex(INDEX_DIR_PATH, fileToUpload, content, currentId);
	}
	
	/*
	 * aici iau id-urile mele din baza de date
	 */
	public long[] getCustomLuceneIds() {
		List<UploadedArticle> allUploadedArticles = uploadedArticleService.getAllArticles();
		long[] customLuceneIds = new long[allUploadedArticles.size()];
		int i=0;
		for(UploadedArticle uploadedArticle : allUploadedArticles) {
			customLuceneIds[i] = uploadedArticle.getUploadedArticleId();
			i++;
		}
		return customLuceneIds;
	}
	
	public Map<Integer, Integer> getCorrespondenceBetweenCustomAndInternalIDs(long[] customLuceneIds) throws IOException {
		
		Map<Integer, Integer> correspondenceMap = new HashMap<Integer, Integer>();
		try {
			LuceneReader reader = new LuceneReader();
			for(int i=0; i<customLuceneIds.length; i++) {
				int internalLuceneId = reader.getDocumentId(customLuceneIds[i]);
				correspondenceMap.put((int) customLuceneIds[i], internalLuceneId);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
		return correspondenceMap;
			
	}
	
	@RequestMapping(value="/correspondence", method = RequestMethod.GET)
	public ResponseEntity<?> getCorrespondence() throws IOException {
		long[] customLuceneIds = getCustomLuceneIds();
		Map<Integer, Integer> correspondence = this.getCorrespondenceBetweenCustomAndInternalIDs(customLuceneIds);
		return new ResponseEntity<>(correspondence, new HttpHeaders(), HttpStatus.OK);
	}
	
		
    @RequestMapping(value="/uploadArticle", method =RequestMethod.POST) 
    @ResponseBody
    public ResponseEntity<?> uploadAnArticle(@RequestParam(value="title")String title,
    		@RequestParam(value="file")MultipartFile file,HttpServletRequest request) throws IOException {
    	
    	String token = request.getHeader("X-Auth-Token");
		
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserRepository.findByUsername(username);
		String titleToBeCompared = title.replace("\"", "");
		String titleToBeSaved = title.replace("\"", "");
		
		String filename = file.getOriginalFilename();
		String filenameWithoutExtension = filename.replaceAll(".pdf", "");
		System.out.println("Replaced filename: " + filenameWithoutExtension);
		
		List<UploadedArticle> allArticles;
		allArticles = (List<UploadedArticle>) uploadedArticleRepository.findAll();
		for(UploadedArticle article: allArticles) {
			if(filename.equals(article.getFilename())) {
				System.out.println("Exista 2 fisiere care au acelasi nume");
				filename = filenameWithoutExtension+"2"+".pdf";
				System.out.println("Filename din conditie:" + filename);
			}
			
		}
			
		String filepath = Paths.get(TEMP_DIR, filename).toString();
		
		try {
			fileUtils.saveFileLocally(filepath, file);
			System.out.println("File uploaded on folder recents");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		PDFBoxUtils pdfUtils = new PDFBoxUtils();
	    String content = pdfUtils.getTextContentFromPDF(filepath);
	   // System.out.println(content);
	    
	    String maxID = uploadedArticleRepository.getMaxId();
	    int maxId = (maxID==null) ? 0 : Integer.parseInt(maxID);
	    int currentId;
	    if(maxId == 0) {
	    	currentId = 1;
	    } else {
	    	currentId = maxId + 1;
	    }
	    String id = Integer.toString(currentId);
	    
	    File fileToUpload = fileUtils.multipartToFile(file);
	    
	    long[] customLuceneIds = this.getCustomLuceneIds();
	    Map<Integer,Integer> correspondenceMap = this.getCorrespondenceBetweenCustomAndInternalIDs(customLuceneIds);
	    
	     
	    Indexer indexer = new Indexer();
	    System.out.println("current id: " + currentId);
	    indexer.createIndex(INDEX_DIR_PATH, fileToUpload, content, currentId);
	    
	    LuceneReader reader = new LuceneReader();
	  	System.out.println("Numarul de fisiere indexate:" + reader.countNumberOfIndexedDocuments());
	  	int currentDocumentLuceneInternalId = reader.getDocumentId(id);
	  	
	  	boolean isSimilar = false;
	  	Iterator it = correspondenceMap.entrySet().iterator();
	  	while(it.hasNext()) {
	  		Map.Entry pair = (Map.Entry)it.next();
	  		LuceneReader reader2 = new LuceneReader();
	  		CosineDocumentSimilarity cosSim = new CosineDocumentSimilarity(reader2.getReader(),
	  				currentDocumentLuceneInternalId, (int) pair.getValue());
	  		double cosineSimilarity = cosSim.getCosineSimilarity();
	  		if(cosineSimilarity > 0.97) {
	  			int similarDocumentCustomId = (int)pair.getKey();
	  			int similarDocumentLuceneId = (int)pair.getValue();
	  			isSimilar = true;
	  			System.out.println("S-a gasit document similar");
	  			break;			
	  		}
	  		
	  		System.out.println("Similaritatea dintre documentul uploadat si documentul" + 
	  		pair.getKey() + " este " + cosineSimilarity);
	  		it.remove();
	  	}
	  	
	  	System.out.println("Inainte de stergere:");
	  	LuceneReader reader4 = new LuceneReader();
	  	System.out.println("Numarul de fisiere indexate:" + reader4.countNumberOfIndexedDocuments());
	  	
	  	
	  	if(isSimilar == true) {
	  		Indexer indexerForDeleting = new Indexer(INDEX_DIR_PATH);
	  		indexerForDeleting.deleteDocumentById(id);
	  		fileUtils.deleteFileFromDirectory(filepath);
	  		System.out.println("Documentul a fost sters din index");
	  		System.out.println("Dupa stergere");
	  		LuceneReader readerAfterDeleting = new LuceneReader();
	  		System.out.println(readerAfterDeleting.countNumberOfIndexedDocuments());
	  		return new ResponseEntity<Response>(new Response("Documentul deja exista"), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	  		
	  	} else {
	  			
	    UploadedArticle articleToUpload = new UploadedArticle(titleToBeSaved,filename);
	    user.getUploadedArticles().add(articleToUpload);
	    articleToUpload.setAppUser(user);   
	    uploadedArticleService.saveUploadedArticle(articleToUpload); 
	    
	    Coauthor[] coauthorsArray = this.getCoauthors(request);
	    String[] userTags = this.getTags(request);
	    
	    if(coauthorsArray!=null) {
	    	for(Coauthor c : coauthorsArray) {
	    		AppUser coauthor  = appUserService.getAppUserById(c.getId());
	    		coauthor.addArticleInCollaboration(articleToUpload);
	    		articleToUpload.addCoauthor(coauthor);
	    	}
	    }
	    
	    if(userTags !=null) {
	    	boolean newUserTags = false;
	    	List<Tag> existingTags = tagService.getAllTags();
	    	Set<String> newTags = new HashSet<String>();
	    	for(Tag t: existingTags) {
	    		for(String userTag: userTags) {
	    			if(!userTag.equals(t.getDenumire())) {
	    				newUserTags = true;
	    				newTags.add(userTag);
	    			}
	    		}
	    	}
	    	
	    	for(String newTag : newTags) {
	    		Tag tag = new Tag(newTag);
	    		tagService.saveTag(tag);
	    	}
	    	
	    	for(String userTag : userTags) {
	    		Tag tagFromDB = tagService.getTagByDenumire(userTag);
	    		tagFromDB.addArticle(articleToUpload);
	    		articleToUpload.addTag(tagFromDB);
	    	}
	    }
	    
	    if(coauthorsArray!=null || userTags!=null) {
	    	uploadedArticleService.saveUploadedArticle(articleToUpload);
	    }
	    
	    return new ResponseEntity<Response>(new Response("Article added"), new HttpHeaders(), HttpStatus.OK);
	  	}
		
    }
    
   
}

