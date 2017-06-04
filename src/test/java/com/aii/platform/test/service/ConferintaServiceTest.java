package com.aii.platform.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Conferinta;
import com.aii.platform.service.ConferintaService;

@Transactional
public class ConferintaServiceTest {
	
	@Autowired
	private ConferintaService conferintaService;
	
//	@Test
//	public void testFindByYear(){
//		String year = "2017";
//		List<Conferinta> confs = conferintaService.getConferintaByYear(year);
//		Assert.assertNotNull("failure-expected not null", confs);
//		//Assert.assertEquals("failure-expected usernames to mach", username, appUser.getUsername());
//		
//	}
	
	

}
