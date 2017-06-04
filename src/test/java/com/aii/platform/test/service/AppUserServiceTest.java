package com.aii.platform.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.service.AppUserService;
import com.aii.platform.test.AbstractTest;

import org.junit.Assert;

import com.aii.platform.models.*;

@Transactional
public class AppUserServiceTest extends AbstractTest {
	
	@Autowired
	private AppUserService appUserService;
	
	@Test
	public void testGetAllUsers() {
		List<AppUser> allAppUsers = appUserService.getAllUsers();
		Assert.assertEquals("failure-expected size", appUserService.countAllUsers(), allAppUsers.size());
			
	}
	
	@Test
	public void testFindOne() {
		Long id = new Long(1);
		AppUser appUser = appUserService.getAppUserById(id);
		Assert.assertNotNull("failure-expected not null", appUser);
		Assert.assertEquals("failure-expected id attribute to match", id, appUser.getId());
	}
	
	@Test
	public void testFindOneNotFound(){
		Long id = Long.MAX_VALUE;
		AppUser appUser = appUserService.getAppUserById(id);
		Assert.assertNull("failure - expected null", appUser);
		
	}
	
	@Test
	public void testFindByUsername(){
		String username = "elena.dobrescu";
		AppUser appUser = appUserService.getAppUserByUsername(username);
		Assert.assertNotNull("failure-expected not null", appUser);
		Assert.assertEquals("failure-expected usernames to mach", username, appUser.getUsername());
		
	}
	
	@Test
	public void testFindByEmail() {
		String email="elena.dobrescu@cnmv.ro";
		AppUser appUser = appUserService.getAppUserByEmail(email);
		Assert.assertNotNull("failure-expected not null", appUser);
		Assert.assertEquals("failure-expected emails to match", email, appUser.getEmail());
	}
	
	@Test 
	public void testFindByArticleId() {
	
		AppUser appUser = appUserService.getAppUserByUploadedArticleId(1L);
		UploadedArticle article = appUser.getUploadedArticleById(1L);
		Assert.assertNotNull("failure-expected not null", appUser);
		Assert.assertEquals("failure-names do not match", "Dobrescu", appUser.getNume());
	}
	
//	@Test
//	public void testCreateAppUser() {
//		AppUser appUser = new AppUser("Popescu", "Maria", "popescu.maria", "123456", "popescu.maria@yahoo.com","ROLE_ADMIN");
//		AppUser createdAppUser = appUserService.saveUser(appUser);
//		Assert.assertNotNull("failure-expected not null", createdAppUser);
//		Assert.assertNotNull("failure-expected id attribute not null", createdAppUser.getId());
//		Assert.assertEquals("failure-expected name to match", "Popescu", createdAppUser.getNume());
//		
//	}
	

}
