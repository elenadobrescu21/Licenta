package com.aii.platform.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aii.platform.models.AppUser;
import com.aii.platform.repository.AppUserRepository;
import com.aii.platform.security.model.SpringSecurityUser;

@Service(value="userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
    private AppUserRepository appUserRepository;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser appUser = this.appUserRepository.findByUsername(username);
		
		if(appUser == null){
			throw new UsernameNotFoundException(String.format("No appUser found with username %s", username));
		} else {
			return new SpringSecurityUser(
					appUser.getId(),
					appUser.getUsername(),
					appUser.getPassword(),
					appUser.getEmail(),
					appUser.getNume(),
					appUser.getPrenume(),
					null,
					AuthorityUtils.commaSeparatedStringToAuthorityList(appUser.getAuthorities())
					);
					
		}
		
	}

}
