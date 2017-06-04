package com.aii.platform.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.repository.UploadedArticleRepository;

import com.aii.platform.models.*;

@Service
public class UploadedArticleService {
	
	@Autowired
	private UploadedArticleRepository uploadedArticleRepository;
	
	public List<UploadedArticle> getAllArticles() {
		return (List<UploadedArticle>) uploadedArticleRepository.findAll();		
	}
	
	public UploadedArticle getArticleById(int i) {
		return uploadedArticleRepository.findOne((long)i);
	}
	
	public UploadedArticle getArticleById(long id) {
		return uploadedArticleRepository.findOne(id);
		
	}
	
	public List<UploadedArticle> getLastThreeUploadedArticles() {
		return uploadedArticleRepository.findFirst3ByOrderByUploadedArticleIdDesc();
	}
	
	public int getMaxId(){
		String maxID = uploadedArticleRepository.getMaxId();
	    int maxId = (maxID==null) ? 0 : Integer.parseInt(maxID);
	    return maxId;
	}
	
	public List<UploadedArticle> getArticlesByAppUserId(Long appUserId) {
		return uploadedArticleRepository.findByAppUserId(appUserId);
		
	}
	
	public long countAllArticles() {
		return uploadedArticleRepository.count();
	}
	
	public UploadedArticle getArticleByFilename(String filename) {
		return uploadedArticleRepository.findByFilename(filename);
	}
	
	public UploadedArticle saveUploadedArticle(UploadedArticle uploadedArticle){
		return uploadedArticleRepository.save(uploadedArticle);
	}
	
	public List<UploadedArticle> getAllArticlesByTagId(Long tagId) {
		return uploadedArticleRepository.findByTagsTagId(tagId);
	}
	
	public List<UploadedArticle> getAllArticlesByDenumireTag(String denumire) {
		return uploadedArticleRepository.findByTagsDenumire(denumire);
	}
	
	public Set<UploadedArticle> getAllArticlesFavouritedByUser(Long appUserId) {
		return uploadedArticleRepository.findByFavouritedById(appUserId);
	}
	
	public List<UploadedArticle> getAllArticlesInCollaborationByAppUserId(Long appUserId){
		return uploadedArticleRepository.findByCoauthorsId(appUserId);
	}
	
	public List<UploadedArticle> getArticleByTitle(String title) {
		return uploadedArticleRepository.findByTitle(title);
	}
	
	public List<UploadedArticle> getArticlesByTipArticolId(Long id) {
		return uploadedArticleRepository.findByTipArticolId(id);
	}
	
	public List<UploadedArticle> getArticlesByAppUserNume(String nume) {
		return uploadedArticleRepository.findByAppUserNume(nume);
	}
	
	public List<UploadedArticle> getArticlesByOwnerNumePrenume(String nume, String prenume) {
		return uploadedArticleRepository.findByOwnerNumePrenume(nume, prenume);
	}
	
	public List<UploadedArticle> getArticlesByCoauthorNumePrenume(String nume, String prenume) {
		return uploadedArticleRepository.findByCoauthorNumePrenume(nume, prenume);
	}
	
	
	
	
	
}
