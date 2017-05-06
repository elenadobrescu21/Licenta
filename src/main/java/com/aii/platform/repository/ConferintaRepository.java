package com.aii.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.Conferinta;

@Repository
public interface ConferintaRepository extends JpaRepository<Conferinta, Long>{
	

}
