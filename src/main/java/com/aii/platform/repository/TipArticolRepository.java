package com.aii.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.TipArticol;
import com.aii.platform.models.UploadedArticle;

@Repository
public interface TipArticolRepository extends JpaRepository<TipArticol, Long> {
	
	public TipArticol findByArticoleUploadedArticleId(@Param("uploadedArticleId") Long id);
	

}
