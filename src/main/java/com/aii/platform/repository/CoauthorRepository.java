package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.Coauthor;
import com.aii.platform.models.Comment;

@Repository
public interface CoauthorRepository extends JpaRepository<Coauthor, Long>{
	
	public Coauthor findByFullname(@Param(value="fullname")String fullname);
	
	public List<Coauthor> findByArticoleUploadedArticleId(@Param(value="id") Long id);

}
