package com.aii.platform.repository;

import com.aii.platform.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface AppUserService {
	
	 AppUser loadAppUserByUsername(String username);

}
