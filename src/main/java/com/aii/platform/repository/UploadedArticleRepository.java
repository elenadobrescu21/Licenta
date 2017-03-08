package com.aii.platform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;

public interface UploadedArticleRepository extends CrudRepository<UploadedArticle, Long>{
	
	public UploadedArticle findByTitle(@Param("title") String title);

}
