package com.aii.platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.CarteCapitol;
import com.aii.platform.models.CarteCompleta;


@Repository
public interface CarteCapitolRepository extends JpaRepository<CarteCapitol, Long> {
	
	public List<CarteCapitol> findByAnPublicare(@Param("anPublicare") int anPublicare);

}
