package com.aii.platform.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aii.platform.dto.AppUserDTO;
import com.aii.platform.models.AppUser;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.UploadedArticleService;

@Component
public class AppUserConverter {
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	public List<AppUserDTO> convertAppUserListToDTO(List<AppUser> users) {
		List<AppUserDTO> appUsersDTO = new ArrayList<AppUserDTO>();
		for(AppUser user: users) {
			AppUserDTO appUserDTO = this.convertToDTOByAppUserId(user.getId());
			appUsersDTO.add(appUserDTO);
		}
		
		return appUsersDTO;
		
	}
	
	public List<AppUserDTO> convertAllAppUsersToDTO() {
		List<AppUser> allUsers = appUserService.getAllUsers();
		return this.convertAppUserListToDTO(allUsers);
		
	}
	
	public AppUserDTO convertToDTOByAppUserId(Long appUserId) {
		AppUser appUser = appUserService.getAppUserById(appUserId);
		String fullName = appUser.getNume() + " " + appUser.getPrenume();
		AppUserDTO appUserDTO = new AppUserDTO(appUser.getId(),fullName, appUser.getUsername(), appUser.getUploadedArticles());
		return appUserDTO;
		
	}

}
