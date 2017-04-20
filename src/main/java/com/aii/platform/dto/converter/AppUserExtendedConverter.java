package com.aii.platform.dto.converter;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aii.platform.dto.AppUserDTO;
import com.aii.platform.dto.AppUserDTOExtended;
import com.aii.platform.dto.UploadedArticleDTO;
import com.aii.platform.models.AppUser;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.UploadedArticleService;

@Component
public class AppUserExtendedConverter {
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@Autowired
	private UploadedArticleConverter uploadedArticleConverter;
	
	public AppUserDTOExtended convertToDTOByAppUserId(Long appUserId) {
		AppUser appUser = appUserService.getAppUserById(appUserId);
		String nume = appUser.getNume();
		String prenume = appUser.getPrenume();
		
		List<UploadedArticle> articoleIncarcate = uploadedArticleService.getArticlesByAppUserId(appUserId);
		Set<UploadedArticle> articoleFavorite = uploadedArticleService.getAllArticlesFavouritedByUser(appUserId);
		List<UploadedArticle> articoleInColaborare = uploadedArticleService.getAllArticlesInCollaborationByAppUserId(appUserId);
		
		List<UploadedArticleDTO> articoleIncarcateDTO = uploadedArticleConverter.convertListToDTO(articoleIncarcate);
		List<UploadedArticleDTO> articoleFavoriteDTO = uploadedArticleConverter.convertSetToDTO(articoleFavorite);
		List<UploadedArticleDTO> articoleInColaborareDTO = uploadedArticleConverter.convertListToDTO(articoleInColaborare);
		
		AppUserDTOExtended appUserDTOExtended = new AppUserDTOExtended(appUser.getId(),
				appUser.getNume(),
				appUser.getPrenume(),
				appUser.getUsername(),
				articoleIncarcateDTO,
				articoleFavoriteDTO,
				articoleInColaborareDTO);
		
		return appUserDTOExtended;
	}

	
}
