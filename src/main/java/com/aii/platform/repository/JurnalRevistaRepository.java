package com.aii.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.JurnalRevista;

@Repository
public interface JurnalRevistaRepository extends JpaRepository<JurnalRevista, Long> {

}
