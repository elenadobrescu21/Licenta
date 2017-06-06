package com.aii.platform.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.*;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long>{
	
	public AppUser findByUsername(@Param("username") String username);
	
	public AppUser findByEmail(@Param("email") String email);

	public List<AppUser> findByCoauthorArticlesUploadedArticleId(@Param("uploadedArticleId")Long uploadedArticleId);
	
	@Query("select a from appUser a join a.uploadedArticles u where (u.title=:titleName)")
	public List<AppUser> findByTitle(@Param("titleName") String titleName);
	
	@Query("select a from appUser a join a.uploadedArticles u where (u.uploadedArticleId=:articleId)")
	public AppUser findByArticleId(@Param("articleId") Long articleId);
	
	@Query("select a from appUser a join a.uploadedArticles u where u.tipArticol.id=1")
	public List<AppUser> findByCarteCompleta();
	
	@Query("select a from appUser a join a.uploadedArticles u where u.tipArticol.id=2")
	public List<AppUser> findByCarteCapitol();
		
	@Query("select a from appUser a join a.uploadedArticles u where u.tipArticol.id=3")
	public List<AppUser> findByConferinta();
	
	@Query("select a from appUser a join a.uploadedArticles u where u.tipArticol=4")
	public List<AppUser> findByJurnalRevista();
	
	@Query("select a from appUser a join a.uploadedArticles u where u.tipArticol.id=:id")
	public List<AppUser> findByTipArticol(@Param("id")Long id);
	
	@Query("select a.nume, a.prenume from appUser a")
	public List<?> findAllByNumePrenume();
	
	


}
