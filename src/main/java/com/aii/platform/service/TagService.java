package com.aii.platform.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.repository.*;

import com.aii.platform.models.Tag;

@Service
public class TagService {
	
	@Autowired
	private TagRepository tagRepository;
	
	public List<Tag> getAllTags(){
		return (List<Tag>) tagRepository.findAll();	
	}
	
	public Tag getTagById(Long tagId) {
		return tagRepository.findOne(tagId);
	}
	
	public Tag getTagByDenumire(String denumire) {
		return tagRepository.findByDenumire(denumire);
	}
	
	

}
