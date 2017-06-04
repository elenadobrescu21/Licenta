package com.aii.platform.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;

public interface UploadedArticleRepository extends JpaRepository<UploadedArticle, Long>{
	
	//public UploadedArticle findByTitle(@Param("title") String title);
	
	public List<UploadedArticle> findByTitle(@Param("title") String title);
	
	public List<UploadedArticle> findFirst3ByOrderByUploadedArticleIdDesc();
	
	public List<UploadedArticle> findByAppUserId(@Param("appUserId") Long appUserId);
	
	public List<UploadedArticle> findByAppUserNume(@Param("nume") String nume);
	
	public List<UploadedArticle> findByCoauthorsNume(@Param("nume") String nume);
	
	public UploadedArticle findByFilename(@Param("filename")String filename);
	
	public List<UploadedArticle> findByTagsTagId(@Param("tagId")Long tagId);
	
	public List<UploadedArticle> findByTagsDenumire(@Param("denumire")String denumire);
	
	public Set<UploadedArticle> findByFavouritedById(@Param("appUserId") Long appUserId);
	
	public List<UploadedArticle> findByCoauthorsId(@Param("coauthorId") Long coauthorId);
	
	public List<UploadedArticle> findByTipArticolId(@Param("tipArticolId") Long tipArticolId);
	
	@Query("select u from uploadedArticle u join u.appUser a where (a.nume=:nume) and (a.prenume=:prenume)")
	public List<UploadedArticle> findByOwnerNumePrenume(@Param("nume") String nume, @Param("prenume") String prenume);
	
	@Query("select u from uploadedArticle u join u.coauthors a where (a.nume=:nume) and (a.prenume=:prenume)")
	public List<UploadedArticle> findByCoauthorNumePrenume(@Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol t where (t.id=:id) and (u.title=:title)")
	public List<UploadedArticle> findByArticleTypeAndTitle(@Param("id") Long id, @Param("title") String title);
	
	@Query("select u from uploadedArticle u join u.tipArticol t join u.appUser a where (t.id=:id) and (a.nume=:nume) and "
			+ "(a.prenume=:prenume)")
	public List<UploadedArticle> findByTipArticolOwnerAutor(@Param("id") Long id, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol t join u.coauthors a where (t.id=:id) and (a.nume=:nume) and "
			+ "(a.prenume=:prenume)")
	public List<UploadedArticle> findByTipArticolOwnerCoautor(@Param("id") Long id, @Param("nume")String nume, @Param("prenume")String prenume);
	
	//cautare tip articol dupa an si autor
	
	@Query("select u from uploadedArticle u join u.appUser a where (a.nume=:nume) and (a.prenume=:prenume) and u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year)")
	public List<UploadedArticle> findConferintaByYearAndAutorOwner(@Param("year")int year, @Param("nume")String nume, @Param("prenume") String prenume);
	
	@Query("select u from uploadedArticle u join u.appUser a where (a.nume=:nume) and (a.prenume=:prenume) and u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year)")
	public List<UploadedArticle> findJurnalRevistaByYearAndAutorOwner(@Param("year")int year, @Param("nume")String nume,@Param("prenume") String prenume);
	
	@Query("select u from uploadedArticle u join u.appUser a where (a.nume=:nume) and (a.prenume=:prenume) and u.uploadedArticleId in (select cc.id from carte_capitol cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCapitolByYearAndAutorOwner(@Param("year") int year, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.appUser a where (a.nume=:nume) and (a.prenume=:prenume) and u.uploadedArticleId in (select cc.id from carte_completa cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCompletaByYearAndAutorOwner(@Param("year") int year, @Param("nume")String nume, @Param("prenume")String prenume);
	
	//cautare tip articol dupa an 
	
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year)")
	public List<UploadedArticle> findConferintaByYear(@Param("year")int year);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year)")
	public List<UploadedArticle> findJurnalRevistaByYear(@Param("year")int year);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select cc.id from carte_capitol cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCapitolByYear(@Param("year") int year);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select cc.id from carte_completa cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCompletaByYear(@Param("year") int year);
	
	//cautare articole dupa tag si tip articol
	@Query("select u from uploadedArticle u join u.tipArticol t join u.tags tag where t.id=:id and tag.denumire=:denumire")
	public List<UploadedArticle> findByTipArticolAndTag(@Param("id") Long id, @Param("denumire")String denumire);
	
	//cautare dupa tip articol, tag si autor
	@Query("select u from uploadedArticle u join u.tipArticol t join u.tags tag join u.appUser a "
			+ "where t.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findByTipArticolAndTagAndAuthor(@Param("id")Long id, @Param("denumire")String denumire,
	@Param("nume") String nume, @Param("prenume") String prenume);
	
	//cautare dupa tag si titlu
	@Query("select u from uploadedArticle u join u.tags t join u.tipArticol ta where ta.id=:id and u.title=:title and t.denumire=:denumire")
	public List<UploadedArticle> findByTagAndTitle(@Param("id")Long id, @Param("title")String title, @Param("denumire")String denumire);
	
	//cautare dupa tag si an
	
	@Query("select u from uploadedArticle u join u.tags tag where tag.denumire=:denumire and u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year)")
	public List<UploadedArticle> findConferintaByYearAndTag(@Param("year")int year, @Param("denumire")String denumire);
	
	@Query("select u from uploadedArticle u join u.tags tag where tag.denumire=:denumire and u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year)")
	public List<UploadedArticle> findJurnalRevistaByYearAndTag(@Param("year")int year, @Param("denumire")String denumire);
	
	@Query("select u from uploadedArticle u join u.tags tag where tag.denumire=:denumire and u.uploadedArticleId in (select cc.id from carte_capitol cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCapitolByYearAndTag(@Param("year") int year, @Param("denumire")String denumire);
	
	@Query("select u from uploadedArticle u join u.tags tag where tag.denumire=:denumire and u.uploadedArticleId in (select cc.id from carte_completa cc where anPublicare=:year)")
	public List<UploadedArticle> findCarteCompletaByYearAndTag(@Param("year") int year, @Param("denumire")String denumire);
	
	//cautare dupa tip, titlu, tag si autor
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ "where ta.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume and u.title=:title")
	public List<UploadedArticle> findByTipArticolTitluTagAndAuthor(@Param("id")Long id, @Param("denumire")String denumire,
			@Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	//cautare dupa toate criteriile -tip, tag, titlu, an, autor
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a"
			+ " where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findConferintaByAllCriteria(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ "where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findJurnalRevistaByAllCriteria(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ "where u.uploadedArticleId in (select cc.id from carte_capitol cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCapitolByAllCriteria(@Param("year") int year,@Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ " where u.uploadedArticleId in (select cc.id from carte_completa cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCompletaByAllCriteria(@Param("year") int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	
	//cautare dupa tip, titlu, tag si an
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag "
			+ " where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title")
	public List<UploadedArticle> findConferintaByTitleTagAndYear(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag "
			+ "where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title")
	public List<UploadedArticle> findJurnalRevistaByTitleTagAndYear(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag "
			+ "where u.uploadedArticleId in (select cc.id from carte_capitol cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title")
	public List<UploadedArticle> findCarteCapitolByTitleTagAndYear(@Param("year") int year,@Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag"
			+ " where u.uploadedArticleId in (select cc.id from carte_completa cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and u.title=:title")
	public List<UploadedArticle> findCarteCompletaByTitleTagAndYear(@Param("year") int year,@Param("id")Long id,
			@Param("denumire")String denumire, @Param("title")String title);
	
	//cautare dupa tip, titlu an si autor
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.appUser a"
			+ " where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year) "
			+ "and ta.id=:id and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findConferintaByTitleYearAndAuthor(@Param("year")int year, @Param("id")Long id,
			 @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.appUser a "
			+ "where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year) "
			+ "and ta.id=:id and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findJurnalRevistaByTitleYearAndAuthor(@Param("year")int year, @Param("id")Long id,
			@Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.appUser a "
			+ "where u.uploadedArticleId in (select cc.id from carte_capitol cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCapitolByTitleYearAndAuthor(@Param("year") int year,@Param("id")Long id,
		 @Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.appUser a "
			+ " where u.uploadedArticleId in (select cc.id from carte_completa cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and u.title=:title and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCompletaByTitleYearAndAuthor(@Param("year") int year,@Param("id")Long id,
			@Param("title")String title, @Param("nume")String nume, @Param("prenume")String prenume);
	
	//cautare dupa tip, titlu si an
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year) "
			+ "and u.title=:title")
	public List<UploadedArticle> findConferintaByTitleAndYear(@Param("year")int year, @Param("title")String title);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year) "
			+ "and u.title=:title")
	public List<UploadedArticle> findJurnalRevistaByTitleAndYear(@Param("year")int year, @Param("title")String title);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select cc.id from carte_capitol cc where cc.anPublicare=:year) "
			+ "and u.title=:title")
	public List<UploadedArticle> findCarteCapitolByTitleAndYear(@Param("year") int year, @Param("title")String title);
	
	@Query("select u from uploadedArticle u where u.uploadedArticleId in (select cc.id from carte_completa cc where cc.anPublicare=:year) "
			+ "and u.title=:title")
	public List<UploadedArticle> findCarteCompletaByTitleAndYear(@Param("year") int year, @Param("title")String title);
	
	//cautare dupa tip, titlu si autor
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.appUser a where "
			+ "ta.id =:id and a.nume=:nume and a.prenume=:prenume and u.title=:title")
	public List<UploadedArticle> findByTitleAndAuthor(@Param("id")Long id, @Param("title")String title,
			@Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.coauthors a where "
			+ "ta.id =:id and a.nume=:nume and a.prenume=:prenume and u.title=:title")
	public List<UploadedArticle> findByTitleAndCoauthor(@Param("id")Long id, @Param("title")String title,
			@Param("nume")String nume, @Param("prenume")String prenume);
	
	//cautare dupa tag, an si autor
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ " where u.uploadedArticleId in (select c.id from conferinta c where year(c.data)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findConferintaTagYearAuthor(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ "where u.uploadedArticleId in (select jr.id from jurnal_revista jr where year(jr.dataAparitie)=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findJurnalRevistaByTagYearAuthor(@Param("year")int year, @Param("id")Long id,
			@Param("denumire")String denumire, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ "where u.uploadedArticleId in (select cc.id from carte_capitol cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCapitolByTagYearAuthor(@Param("year") int year,@Param("id")Long id,
			@Param("denumire")String denumire, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select u from uploadedArticle u join u.tipArticol ta join u.tags tag join u.appUser a "
			+ " where u.uploadedArticleId in (select cc.id from carte_completa cc where cc.anPublicare=:year) "
			+ "and ta.id=:id and tag.denumire=:denumire and a.nume=:nume and a.prenume=:prenume")
	public List<UploadedArticle> findCarteCompletaByTagYearAuthor(@Param("year") int year,@Param("id")Long id,
			@Param("denumire")String denumire, @Param("nume")String nume, @Param("prenume")String prenume);
	
	@Query("select max(u.uploadedArticleId) from uploadedArticle u")
	public String getMaxId();
	
	
}
