package com.aii.platform.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.test.AbstractTest;

@Transactional
public class UploadedArticleServiceTest extends AbstractTest{
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	
	
	@Test
	public void testGetAllArticles() {
		List<UploadedArticle> allArticles = uploadedArticleService.getAllArticles();
		Assert.assertEquals("failure-expected size", uploadedArticleService.countAllArticles(), allArticles.size());
			
	}
	
	@Test
	public void testFindOne() {
		Long id = new Long(1);
		UploadedArticle uploadedArticle = uploadedArticleService.getArticleById(1);
		Assert.assertNotNull("failure-expected not null", uploadedArticle);
		Assert.assertEquals("failure-expected id attribute to match", id, uploadedArticle.getUploadedArticleId());
	}
	
	@Test
	public void testFindOneNotFound(){
		Long id = Long.MAX_VALUE;
		UploadedArticle uploadedArticle = uploadedArticleService.getArticleById(id);
		Assert.assertNull("failure - expected null", uploadedArticle);
		
	}
	
	
	@Test
	public void testCreateUploadedArticle() {
		UploadedArticle newUploadedArticle = new UploadedArticle("Test", "test.pdf");
		AppUser owner = appUserService.getAppUserById(1);
		newUploadedArticle.setAppUser(owner);
		UploadedArticle createdUploadedArticle = uploadedArticleService.saveUploadedArticle(newUploadedArticle);
		Assert.assertNotNull("failure-expected not null", createdUploadedArticle);
		Assert.assertNotNull("failure-expected id attribute not null", createdUploadedArticle.getUploadedArticleId());
		Assert.assertEquals("failure-expected name to match", "Test", createdUploadedArticle.getTitle());
		
	}
	
	@Test
	public void testFindByTagId() {
		List<UploadedArticle> uploadedArticles = uploadedArticleService.getAllArticlesByTagId(1L);
		for(UploadedArticle u : uploadedArticles) {
			logger.debug("Titlu articol "+ u.getTitle());
			System.out.println("Titlu articol: " + u.getTitle());
		}
		Assert.assertNotNull("failure-expected not null", uploadedArticles);
		Assert.assertEquals("failure-expected name match","Rezolvare", uploadedArticles.get(0).getTitle());
		
	}
	
	@Test
	public void testFindByDenumireTag() {
		List<UploadedArticle> uploadedArticles = uploadedArticleService.getAllArticlesByDenumireTag("poo");
		Assert.assertNotNull("failure-expected not null", uploadedArticles);
		Assert.assertEquals("failure-expected name match","Rezolvare", uploadedArticles.get(0).getTitle());
	}
	

	
	
}
