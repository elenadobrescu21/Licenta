package com.aii.platform.test.relations;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.test.AbstractTest;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;


@Transactional
public class AppUserRelations extends AbstractTest{
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired UploadedArticleService uploadedArticleService;
	
	
	@Test
	public void testAddArticle() {
		UploadedArticle uploadedArticle = new UploadedArticle("Articol test", "test.pdf", "abstract");
		AppUser appUser = appUserService.getAppUserById(1L);
		appUser.addArticle(uploadedArticle);
		uploadedArticle.setAppUser(appUser);
		uploadedArticleService.saveUploadedArticle(uploadedArticle);
		UploadedArticle recentlyUploadedArticle = uploadedArticleService.getArticleByFilename("test.pdf");
		Assert.assertNotNull("failure-expected not null", recentlyUploadedArticle);
		Assert.assertEquals("failure-expected owners to match", "Dobrescu", recentlyUploadedArticle.getAppUser().getNume());
		
	}
	
	@Test
	public void testAddArticleToFavourites(){
		AppUser appUser = appUserService.getAppUserById(1L);
		UploadedArticle article = uploadedArticleService.getArticleById(1L);
		appUser.addArticleToFavourites(article);
		AppUser modifiedAppUser = appUserService.saveUser(appUser);
		//Set<UploadedArticle> favouriteArticles = uploadedArticleService.getAllArticlesFavouritedByUser(modifiedAppUser.getId());
		//Assert.assertNotNull("failure-expected not null", favouriteArticles);
	}
	
	@Test
	public void testCoauthor() {
		AppUser appUser = appUserService.getAppUserById(1L);
		UploadedArticle article = uploadedArticleService.getArticleById(1L);
		appUser.addArticleInCollaboration(article);
		AppUser modifiedAppUser = appUserService.saveUser(appUser);
		List<UploadedArticle> articlesInCollaboration = uploadedArticleService.getAllArticlesInCollaborationByAppUserId(modifiedAppUser.getId());
		Assert.assertNotNull("failure-expected not null", articlesInCollaboration);
		
	}

}
