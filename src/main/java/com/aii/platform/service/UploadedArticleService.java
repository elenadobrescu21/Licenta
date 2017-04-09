package com.aii.platform.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public UploadedArticle getArticleById(Long articleId) {
		return uploadedArticleRepository.findOne(articleId);
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
	public UploadedArticle getArticleByFilename(String filename) {
		return uploadedArticleRepository.findByFilename(filename);
	}
	
}
