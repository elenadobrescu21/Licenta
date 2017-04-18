package com.aii.platform.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.service.TagService;
import com.aii.platform.test.AbstractTest;
import com.aii.platform.controllers.UploadController;
import com.aii.platform.controllers.UploadedArticleController;
import com.aii.platform.models.*;

@Transactional
public class TagServiceTest extends AbstractTest{
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private UploadController uploadController;
	
	private List<Tag> allTags;
	
	@Before
	public void setup(){
		allTags = tagService.getAllTags();
	}
	
	@Test
	public void testFindAll() {
		Assert.assertNotNull("failure-expected not null", allTags);
		Assert.assertEquals("failure-expected same size",tagService.countAllTags(), allTags.size());
				
	}
	
	@Test
	public void testGetAllTagsByArticleId() {
		List<Tag> allTagsforArticle = tagService.getAllTagsByArticleId(1L);
		Assert.assertNotNull("failure-expected not null", allTagsforArticle);
		
	}
	
	@Test
	public void testGetTagByDenumire() {
		Tag tag = tagService.getTagByDenumire("poo");
		Assert.assertNotNull("failure-expected not null", tag);
		Assert.assertEquals("failure-expected names to match", "poo", tag.getDenumire());
	}
	
	@Test
	public void testIfTagExistsWhenTrue() {
		boolean exists = uploadController.checkIfTagAlreadyExists(allTags, "poo");
		Assert.assertTrue(exists);
		
	}
	
	@Test
	public void testIfTagExistsWhenFalse() {
		boolean exists = uploadController.checkIfTagAlreadyExists(allTags, "altceva");
		Assert.assertFalse(exists);
		
	}
	
	

}
