package com.aii.platform.test.service;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.service.AppUserService;
import com.aii.platform.test.AbstractTest;

public class AppUserServiceMockito extends AbstractTest{
	
	@Mock 
	private AppUserRepository appUserRepository;
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Test
	public void testFindById(){
		MockitoAnnotations.initMocks(this);
		AppUserService appUserService = new AppUserService();
		appUserService.getAppUserById(1L);
		Mockito.verify(appUserRepository).findOne(1L);
		
	}
	
	
	

}
