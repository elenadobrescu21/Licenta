package com.aii.platform.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aii.platform.models.*;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser,Integer>{
	
	public AppUser findOneByUsername(@Param("username") String username);

}
