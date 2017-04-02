package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;

public interface UploadedArticleRepository extends CrudRepository<UploadedArticle, Long>{
	
	public UploadedArticle findByTitle(@Param("title") String title);
	
	public List<UploadedArticle> findFirst3ByOrderByUploadedOnDesc();
	
	public List<UploadedArticle> findByAppUserId(@Param("appUserId") Long appUserId);
	
	@Query("select max(u.uploadedArticleId) from uploadedArticle u")
	public String getMaxId();
	

}
