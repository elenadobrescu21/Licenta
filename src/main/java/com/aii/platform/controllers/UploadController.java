package com.aii.platform.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

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
import com.aii.platform.models.CarteCapitol;
import com.aii.platform.models.CarteCompleta;
import com.aii.platform.models.Tag;
import com.aii.platform.models.TipArticol;
import com.aii.platform.models.Coauthor;
import com.aii.platform.models.Conferinta;
import com.aii.platform.models.JurnalRevista;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.repository.TagRepository;
import com.aii.platform.repository.UploadedArticleRepository;
import com.aii.platform.response.Response;
import com.aii.platform.security.TokenUtils;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.CarteCapitolService;
import com.aii.platform.service.CarteCompletaService;
import com.aii.platform.service.CoauthorService;
import com.aii.platform.service.ConferintaService;
import com.aii.platform.service.JurnalRevistaService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.TipArticolService;
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
	
	@Autowired
	private TokenUtils tokenUtils;
	
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
	
	@Autowired
	private CoauthorService coauthorService;
	
	@Autowired
	private TipArticolService tipArticolService;
	
	@Autowired
	private CarteCapitolService carteCapitolService;
	
	@Autowired
	private CarteCompletaService carteCompletaService;
	
	@Autowired
	private ConferintaService conferintaService;
	
	@Autowired
	private JurnalRevistaService jurnalRevistaService;
	
	public TipArticol getTipArticol(HttpServletRequest request) {
		String tipArticolString = request.getParameter("article-type");
		TipArticol tipArticol = new TipArticol();
		if(tipArticolString!=null && tipArticolString.length() > 2) {
			tipArticol = new Gson().fromJson(tipArticolString, TipArticol.class);
		}
		
		return tipArticol;
		
	}
	
	public Conferinta getConferinta(HttpServletRequest request) {
		String numeConferinta = request.getParameter("nume-conferinta").replace("\"", "");
		String locatieConferinta = request.getParameter("locatie-conferinta").replace("\"", "");;
		String dataConferintaString = request.getParameter("data-conferinta").replace("\"", "");;
		System.out.println("Nume conferinta" + numeConferinta);
		System.out.println("Data conferinta String" + dataConferintaString);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataConferinta = null;
		try {
			dataConferinta = format.parse(dataConferintaString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Data conferinta" + dataConferinta.toString());
		Conferinta conferinta = new Conferinta(numeConferinta, locatieConferinta, dataConferinta);
		return conferinta;
	}
	
	public CarteCompleta getCarteCompleta(HttpServletRequest request) {
		String editura = request.getParameter("editura-carte-completa").replace("\"", "");
		String editie = request.getParameter("editie-carte-completa").replace("\"", "");
		String anAparitieString = request.getParameter("an-aparitie-carte-completa").replace("\"", "");
		String issn = request.getParameter("issn-carte-completa").replace("\"", "");
		String isbn = request.getParameter("isbn-carte-completa").replace("\"", "");
		int anAparitie = Integer.parseInt(anAparitieString);
		
		CarteCompleta carteCompleta = new CarteCompleta(editura, editie, isbn, issn, anAparitie);
		return carteCompleta;
		
	}
	
	public CarteCapitol getCarteCapitol(HttpServletRequest request) {
		String titluCarte = request.getParameter("titlu-carte-capitol").replace("\"", "");
		String numeCapitol = request.getParameter("nume-capitol-carte").replace("\"", "");
		String autoriCarte = request.getParameter("autori-capitol-carte");
		String editoriCarte = request.getParameter("editori-capitol-carte");
		
		String[] autoriCarteArray = null;
		if(autoriCarte!=null && autoriCarte.length() > 2) {
			autoriCarteArray =  new Gson().fromJson(autoriCarte, String[].class);	
		}
		
		String autoriArray2 = StringUtils.join(autoriCarteArray, ", ");
		
		System.out.println("Autori array 2:" + autoriArray2);
		String joinedAutori = null;
		for(String autor : autoriCarteArray) {
			joinedAutori = String.join(",", autor);
		}
		
		System.out.println("joinedAutori:" + joinedAutori);
		
		String[] editoriCarteArray = null;
		if(editoriCarte!=null && editoriCarte.length() > 2) {
			editoriCarteArray =  new Gson().fromJson(editoriCarte, String[].class);	
		}
		
		String editoriArray2 = StringUtils.join(editoriCarteArray, ", ");
		
		String anAparitieString = request.getParameter("an-aparitie-capitol-carte").replace("\"", "");
		String issn = request.getParameter("issn-capitol-carte").replace("\"", "");
		String isbn = request.getParameter("isbn-capitol-carte").replace("\"", "");
		String editura = request.getParameter("editura-capitol-carte").replace("\"", "");
		String editie = request.getParameter("editie-carte-capitol").replace("\"", "");
		String paginaInceputString = request.getParameter("pagina-inceput-carte-capitol").replace("\"", "");
		String paginaSfarsitString = request.getParameter("pagina-sfarsit-carte-capitol").replace("\"", "");
		
		int anAparitie = Integer.parseInt(anAparitieString);
		int paginaInceput = Integer.parseInt(paginaInceputString);
		int paginaSfarsit = Integer.parseInt(paginaSfarsitString);
		
		System.out.println("Autori carte " + autoriCarte);
		System.out.println("Editori carte " + editoriCarte);
		
		CarteCapitol carteCapitol = new CarteCapitol(titluCarte, autoriArray2,editoriArray2, numeCapitol,
				paginaInceput, paginaSfarsit, anAparitie, editura, editie, isbn, issn);
		
		return carteCapitol;
		
		
	}
	
	public JurnalRevista getJurnalRevista(HttpServletRequest request) {
		String numeRevista = request.getParameter("nume-revista").replace("\"", "");
		String numarRevistaString = request.getParameter("numar-revista").replace("\"", "");
		String volumRevistaString = request.getParameter("volum-revista").replace("\"", "");
		String dataAparitieRevistaString = request.getParameter("data-aparitie-revista").replace("\"", "");
		String issn = request.getParameter("issn-revista").replace("\"", "");
		String isbn = request.getParameter("isbn-revista").replace("\"", "");
		String paginaInceput = request.getParameter("pagina-inceput-revista").replace("\"", "");
		String paginaSfarsitString = request.getParameter("pagina-sfarsit-revista").replace("\"", "");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dataAparitieRevista = null;
		try {
			dataAparitieRevista = format.parse(dataAparitieRevistaString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int numarRevista = Integer.parseInt(numarRevistaString);
		int volumRevista = Integer.parseInt(volumRevistaString);
		int paginaStart = Integer.parseInt(paginaInceput);
		int paginaSfarsit = Integer.parseInt(paginaSfarsitString);
		
		JurnalRevista jurnalRevista = new JurnalRevista(numeRevista,numarRevista, volumRevista, paginaStart,
				paginaSfarsit, issn, isbn, dataAparitieRevista);
		
		return jurnalRevista;
			
	}
	
	
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
			for(String userTag: userTags) {
				userTag = userTag.toLowerCase();
			}
		}
		
		return userTags;
	}
	
	public String[] getCoauthorsWithoutAccount(HttpServletRequest request) {
		String coauthorsWithoutAccount = request.getParameter("coauthors-without-account");
		String[] coauthors = null;
		if(coauthorsWithoutAccount !=null && coauthorsWithoutAccount.length() > 2) {
			coauthors = new Gson().fromJson(coauthorsWithoutAccount, String[].class);
		}
		return coauthors;
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
	
		
    @RequestMapping(value="/upload", method =RequestMethod.POST) 
    @ResponseBody
    public ResponseEntity<?> uploadAnArticle(@RequestParam(value="title")String title,
    		@RequestParam(value="file")MultipartFile file, @RequestParam(value="abstract") String abstractSectionParam,
    		@RequestParam(value="wos") String wosParam,
    		@RequestParam(value="doi") String doiParam,
    		HttpServletRequest request) throws IOException {
    	
    	String token = request.getHeader("X-Auth-Token");
		
		String username = tokenUtils.getUsernameFromToken(token);
		AppUser user = appUserService.getAppUserByUsername(username);
		
		TipArticol tipArticol = this.getTipArticol(request);
		System.out.println("Tip articol: "  + tipArticol.getDenumire());
		
		Long tipArticolId = tipArticol.getId();
		
		String titleToBeCompared = title.replace("\"", "");
		String titleToBeSaved = title.replace("\"", "");
		
		String filename = file.getOriginalFilename();
		String filenameWithoutExtension = filename.replaceAll(".pdf", "");
		
		String wos = request.getParameter("wos");
		String doi = request.getParameter("doi");
		
		String abstractSection = request.getParameter("abstract");
		System.out.println("Replaced filename: " + filenameWithoutExtension);
		
		List<UploadedArticle> allArticles = uploadedArticleService.getAllArticles();
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
	  		return new ResponseEntity<Response>(new Response("Documentul deja exista"), new HttpHeaders(), HttpStatus.IM_USED);
	  		
	  	} else {
	  			
	    UploadedArticle articleToUpload = new UploadedArticle(titleToBeSaved,filename, abstractSection,wos,doi);
	    TipArticol tipArticol2 = tipArticolService.getTipArticolById(tipArticolId);
	    user.getUploadedArticles().add(articleToUpload);
	    articleToUpload.setAppUser(user); 
	    tipArticol2.addArticol(articleToUpload);
	    articleToUpload.setTipArticol(tipArticol2);
	    uploadedArticleService.saveUploadedArticle(articleToUpload); 
	    
	    Coauthor[] coauthorsArray = this.getCoauthors(request);
	    String[] userTags = this.getTags(request);
	    String[] coauthorsWithoutAccount = this.getCoauthorsWithoutAccount(request);
	    
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
	    	for(String userTag : userTags) {
	    		boolean exists = this.checkIfTagAlreadyExists(existingTags, userTag);
	    		if(exists == false) {
	    			newTags.add(userTag);
	    		}
	    	}
	    	
	    	System.out.println("taguri noi:");
	    	for(String newTag : newTags) {
	    		System.out.println("newTag: " + newTag);
	    		Tag tag = new Tag(newTag);
	    		tagService.saveTag(tag);
	    	}
	    	
	    	for(String userTag : userTags) {
	    		System.out.println("User tag:" + userTag);
	    		Tag tagFromDB = tagService.getTagByDenumire(userTag);
	    		tagFromDB.addArticle(articleToUpload);
	    		articleToUpload.addTag(tagFromDB);
	    	}
	    }
	    
	    if(coauthorsWithoutAccount!=null) {
	    	boolean newCoauthorsWithoutAccount = false;
	    	List<Coauthor> existingCoauthors = coauthorService.getAllCoauthors();
	    	Set<String> newCoauthors = new HashSet<String>();
	    	for(String c : coauthorsWithoutAccount) {
	    		boolean alreadyExisting = this.checkIfCoauthorAlreadyExists(existingCoauthors, c);
	    		if(alreadyExisting == false) {
	    			newCoauthors.add(c);
	    		}
	    	}
	    	
	    	for(String newCoauthor : newCoauthors) {
	    		Coauthor coauthor = new Coauthor(newCoauthor);
	    		coauthorService.saveCoauthor(coauthor);
	    	}
	    	
	    	for(String coauthorWithoutAccount : coauthorsWithoutAccount ) {
	    		Coauthor coauthorFromDb = coauthorService.getCoauthorByFullname(coauthorWithoutAccount);
	    		coauthorFromDb.adaugaArticol(articleToUpload);
	    		articleToUpload.addCoauthorWithoutAccount(coauthorFromDb);
	    	}
	    	
	    }
	    
	    if(coauthorsArray!=null || userTags!=null || coauthorsWithoutAccount!=null) {
	    	uploadedArticleService.saveUploadedArticle(articleToUpload);
	    }
	    
	    List<UploadedArticle> articleByTitle = uploadedArticleService.getArticleByTitle(titleToBeSaved);
	    UploadedArticle recentlyUploadedArticle = articleByTitle.get(0);
	    
	    if(tipArticolId == 1) {
	    	CarteCompleta carteCompleta = this.getCarteCompleta(request);
	    	carteCompleta.setUploadedArticle(recentlyUploadedArticle);
	    	carteCompletaService.saveCarteCompleta(carteCompleta);
	    }
	    
	    if(tipArticolId == 2) {
	    	CarteCapitol carteCapitol = this.getCarteCapitol(request);
	    	carteCapitol.setUploadedArticle(articleToUpload);
	    	carteCapitolService.saveCarteCapitol(carteCapitol);
	    	System.out.println("Autori carte capitol" + carteCapitol.getAutoriCarte());
	    }
	    
	    if(tipArticolId == 3) {
	    	Conferinta conferinta = this.getConferinta(request);
	    	conferinta.setUploadedArticle(recentlyUploadedArticle);
	    	conferintaService.saveConferinta(conferinta);
	    }
	    
	    if(tipArticolId == 4) {
	    	JurnalRevista jurnalRevista = this.getJurnalRevista(request);
	    	jurnalRevista.setUploadedArticle(recentlyUploadedArticle);
	    	jurnalRevistaService.saveJurnalRevista(jurnalRevista);
	    	
	    }
	    
	    return new ResponseEntity<Response>(new Response("Article added"), new HttpHeaders(), HttpStatus.OK);
	  	}
		
    }
    
    public boolean checkIfTagAlreadyExists(List<Tag> existingTags, String tagToBeCompared){
    	boolean exists = false;
    	for(Tag t: existingTags) {
    		if(tagToBeCompared.equals(t.getDenumire())) {
    			exists = true;
    			break;
    		}
    	}
    	return exists;
    }
    
    public boolean checkIfCoauthorAlreadyExists(List<Coauthor> existingCoauthors, String coauthorToBeCompared) {
    	boolean exists = false;
    	for(Coauthor c: existingCoauthors ) {
    		if(coauthorToBeCompared.equals(c.getFullname())) {
    			exists = true;
    			break;
    		}
    	}
    	return exists;
    }
    
   
}

