package com.aii.platform.test.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.aii.platform.service.TagService;
import com.aii.platform.test.AbstractTest;

import com.aii.platform.models.*;

@Transactional
public class TagServiceTest extends AbstractTest{
	
	@Autowired
	private TagService tagService;
	
	@Test
	public void testFindAll() {
		List<Tag> allTags = tagService.getAllTags();
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
	
	

}
