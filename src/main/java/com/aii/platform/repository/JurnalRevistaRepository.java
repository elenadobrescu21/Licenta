package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.Conferinta;
import com.aii.platform.models.JurnalRevista;

@Repository
public interface JurnalRevistaRepository extends JpaRepository<JurnalRevista, Long> {
	
	@Query("select c from jurnal_revista c where year(c.dataAparitie)=:year")
	public List<JurnalRevista> findByYear(@Param("year") int year);

}
