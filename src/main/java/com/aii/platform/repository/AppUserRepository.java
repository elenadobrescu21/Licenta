package com.aii.platform.repository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.*;


public interface AppUserRepository extends CrudRepository<AppUser,Long>{
	
	public AppUser findByUsername(@Param("username") String username);
	public AppUser findByEmail(@Param("email") String email);
	
	@Query("select a from appUser a join a.uploadedArticles u where (u.title=:titleName)")
	public List<AppUser> findByTitle(@Param("titleName") String titleName);
	
	@Query("select a from appUser a join a.uploadedArticles u where (u.uploadedArticleId=:articleId)")
	public AppUser findByArticleId(@Param("articleId") Long articleId);
	

}
