package com.aii.platform.test.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.service.AppUserService;
import com.aii.platform.test.AbstractTest;

import junit.framework.Assert;

import com.aii.platform.models.*;

@Transactional
public class AppUserServiceTest extends AbstractTest {
	
	@Autowired
	private AppUserService appUserService;
	
	@Test
	public void testGetAllUsers() {
		List<AppUser> allAppUsers = appUserService.getAllUsers();
		Assert.assertEquals("failure-expected size", 2, allAppUsers.size());
		
		
	}

}
