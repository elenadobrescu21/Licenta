package com.aii.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.CarteCompleta;

@Repository
public interface CarteCompletaRepository extends JpaRepository<CarteCompleta, Long>{

}
