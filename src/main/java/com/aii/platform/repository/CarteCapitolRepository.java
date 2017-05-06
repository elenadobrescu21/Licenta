package com.aii.platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.CarteCapitol;


@Repository
public interface CarteCapitolRepository extends JpaRepository<CarteCapitol, Long> {

}
