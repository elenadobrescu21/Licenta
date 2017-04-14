package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.*;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	public Tag findByDenumire(@Param("denumire")String denumire);
	
	public List<Tag> findByArticlesUploadedArticleId(@Param("articleId") Long articleId);
	
	@Query("select max(t.tagId) from tag t")
	public String getMaxId();

}
