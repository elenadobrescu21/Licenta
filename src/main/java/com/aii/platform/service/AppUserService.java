package com.aii.platform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.AppUser;
import com.aii.platform.repository.AppUserRepository;

@Service
public class AppUserService {
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	public List<AppUser> getAllUsers() {	
		return (List<AppUser>) appUserRepository.findAll();				
	}
	
	public AppUser getAppUserById(long id) {
		return appUserRepository.findOne(id);
		
	}
	
	public AppUser getAppUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}
	
	public AppUser getAppUserByEmail(String email) {
		return appUserRepository.findByEmail(email);
	}
	
	public AppUser getAppUserByUploadedArticleId(Long articleId) {
		return appUserRepository.findByArticleId(articleId);
		
	}
	
	public void deleteAppUserById(Long appUserId) {
		appUserRepository.delete(appUserId);
	}
	
	public AppUser saveUser(AppUser appUser) {
		return appUserRepository.save(appUser);
	}
	
	public List<AppUser> getCoauthorsByArticleId(Long articleId) {
		return appUserRepository.findByCoauthorArticlesUploadedArticleId(articleId);
	}
	
	public long countAllUsers(){
		return appUserRepository.count();
	}
	

}
