package com.aii.platform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.aii.platform.models.*;

public interface TagRepository extends CrudRepository<Tag, Long> {
	
	public Tag findByDenumire(@Param("denumire")String denumire);

}
