package com.aii.platform.dto.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aii.platform.dto.AppUserDTO;
import com.aii.platform.dto.UploadedArticleDTO;
import com.aii.platform.models.AppUser;
import com.aii.platform.models.Tag;
import com.aii.platform.models.UploadedArticle;
import com.aii.platform.service.AppUserService;
import com.aii.platform.service.TagService;
import com.aii.platform.service.UploadedArticleService;

@Component
public class UploadedArticleConverter {
	
	@Autowired
	private UploadedArticleService uploadedArticleService;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private TagService tagService;
	
	public List<UploadedArticleDTO> convertArticleListToDTO() {
		List<UploadedArticle> allArticles = uploadedArticleService.getAllArticles();
		return this.convertListToDTO(allArticles);
	}
	
		
	public List<UploadedArticleDTO> convertLatestArticleListToDTO() {
		List<UploadedArticle> latestArticles = uploadedArticleService.getLastThreeUploadedArticles();
		return this.convertListToDTO(latestArticles);
	}
	
	public UploadedArticleDTO convertUploadedArticleToDTO(Long articleId) {
		UploadedArticle requestedUploadedArticle = uploadedArticleService.getArticleById(articleId);
		AppUser owner = appUserService.getAppUserByUploadedArticleId(requestedUploadedArticle.getUploadedArticleId());
		List<AppUser> coauthors = appUserService.getCoauthorsByArticleId(requestedUploadedArticle.getUploadedArticleId());
		List<Tag> tagList = tagService.getAllTagsByArticleId(requestedUploadedArticle.getUploadedArticleId());
		
		String ownerFullName = owner.getNume() + " " + owner.getPrenume();
		Long ownerId = owner.getId();
		String username = owner.getUsername();
		AppUserDTO ownerDTO = new AppUserDTO(ownerId, ownerFullName, username);
		List<AppUserDTO> coauthorsList = new ArrayList<AppUserDTO>();
		String[] tags = new String[tagList.size()];
		
		int i = 0;
		for(AppUser c: coauthors) {
//			coauthorFullNames[i++] = c.getNume() + " " + c.getPrenume();
			String fullName = c.getNume() + " " + c.getPrenume();
			Long id = c.getId();
			String userName = c.getUsername();
			coauthorsList.add(new AppUserDTO(id, fullName, username));
		}
		i = 0;
		for(Tag t:tagList){
			tags[i++] = t.getDenumire();
		}
		
		UploadedArticleDTO uploadedArticleDTO = new UploadedArticleDTO(requestedUploadedArticle.getUploadedArticleId(),
				requestedUploadedArticle.getTitle(),
				requestedUploadedArticle.getFilename(),
				requestedUploadedArticle.getNumberOfDownloads(),
				requestedUploadedArticle.getUploadedOn(),
				ownerDTO,
				coauthorsList,
				tags);
		
		return uploadedArticleDTO;
	}
	
	public List<UploadedArticleDTO> convertListToDTO(List<UploadedArticle> allArticles) {
		List<UploadedArticleDTO> allArticlesDTO = new ArrayList<UploadedArticleDTO>();
		
		for(UploadedArticle article: allArticles) {
			UploadedArticleDTO articleDTO = this.convertUploadedArticleToDTO(article.getUploadedArticleId());
			allArticlesDTO.add(articleDTO);		
		}
		
		return allArticlesDTO;
	}

}
