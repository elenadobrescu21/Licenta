package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.CarteCompleta;

@Repository
public interface CarteCompletaRepository extends JpaRepository<CarteCompleta, Long>{
	
	public List<CarteCompleta> findByAnPublicare(@Param("anPublicare") int anPublicare);

}
