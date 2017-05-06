package com.aii.platform.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.models.CarteCompleta;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.UploadedArticleService;
import com.aii.platform.test.AbstractTest;
import com.aii.platform.service.*;


@Transactional
public class CarteCompletaServiceTest extends AbstractTest{
	
	@Autowired
	private CarteCompletaService carteCompletaService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	
	
	@Test
	public void testAddCarteCompleta(){
		
		UploadedArticle article = uploadedArticleService.getArticleById(1L);
		CarteCompleta carteCompleta = new CarteCompleta("Minerva", "4", "1235-5622-56-12364", "1234-5678",2005);
		carteCompleta.setUploadedArticle(article);
		CarteCompleta carteCompleta2 = carteCompletaService.saveCarteCompleta(carteCompleta);
		UploadedArticle article2 = carteCompleta2.getUploadedArticle();
		Assert.assertNotNull("failure-expected not null", article2);
		//Assert.assertEquals("failure-expected id attribute to match", 1L, article2.getUploadedArticleId());
		
	}
	

}
