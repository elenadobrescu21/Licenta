package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;

public interface UploadedArticleRepository extends JpaRepository<UploadedArticle, Long>{
	
	public UploadedArticle findByTitle(@Param("title") String title);
	
	public List<UploadedArticle> findFirst3ByOrderByUploadedArticleIdDesc();
	
	public List<UploadedArticle> findByAppUserId(@Param("appUserId") Long appUserId);
	
	public UploadedArticle findByFilename(@Param("filename")String filename);
	
	public List<UploadedArticle> findByTagsTagId(@Param("tagId")Long tagId);
	
	public List<UploadedArticle> findByTagsDenumire(@Param("denumire")String denumire);
	
	public List<UploadedArticle> findByFavouritedById(@Param("appUserId") Long appUserId);
	
	public List<UploadedArticle> findByCoauthorsId(@Param("coauthorId") Long coauthorId);
	
	@Query("select max(u.uploadedArticleId) from uploadedArticle u")
	public String getMaxId();
	
	
}
