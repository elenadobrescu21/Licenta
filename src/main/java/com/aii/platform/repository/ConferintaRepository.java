package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.AppUser;
import com.aii.platform.models.Conferinta;

@Repository
public interface ConferintaRepository extends JpaRepository<Conferinta, Long>{
	
	@Query("select c from conferinta c where year(c.data)=:year")
	public List<Conferinta> findByYear(@Param("year") int year);
	
	
	

}
