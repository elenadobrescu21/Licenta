package com.aii.platform.utils;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

@Service
public class DiacriticsUtils {
	
	public boolean checkForDiacritics(String stringToBeChecked) {
		boolean hasDiacritics = false;
		
		for(int i=0; i<stringToBeChecked.length(); i++) {
			char c = stringToBeChecked.charAt(i);
			String character = Character.toString(c);
			switch(character) {
			case "Ă" : hasDiacritics = true;
					   break;			
			case "ă" : hasDiacritics = true;
					   break;				
			case "Î" : hasDiacritics = true;
					   break;				
			case "î" : hasDiacritics = true;
					   break;	
			case "Ș" : hasDiacritics = true;
					   break;					
			case "ș" : hasDiacritics = true;
					   break;	
			case "Ț" : hasDiacritics = true;
					   break;				
			case "ț" : hasDiacritics = true;
					   break;
			case "Â" : hasDiacritics = true;
					   break;				
			case "â" : hasDiacritics = true;
				       break;
			}
		}
		return hasDiacritics;
	}
	
	public boolean characterIsDiacritic(String character) throws UnsupportedEncodingException {
		boolean isDiacritic = false;
		switch(character) {
		case "Ă" : isDiacritic = true;
				   break;			
		case "ă" : isDiacritic = true;
				   break;				
		case "Î" : isDiacritic = true;
				   break;				
		case "î" : isDiacritic = true;
				   break;	
		case "Ș" : isDiacritic = true;
				   break;					
		case "ș" : isDiacritic = true;
				   break;	
		case "Ț" : isDiacritic= true;
				   break;				
		case "ț" : isDiacritic = true;
				   break;
		case "Â" : isDiacritic = true;
				   break;				
		case "â" : isDiacritic = true;
			       break;
		}
		return isDiacritic;
				
	}
	
	public String normalizeCharacter(String character) throws UnsupportedEncodingException {
		String normalizedCharacter = new String();	
		switch(character) {
		case "ă" : normalizedCharacter = "a";
				   break;			
		case "ț" : normalizedCharacter = "t";
				   break;				
		case "î" : normalizedCharacter = "i";
				   break;				
		case "ș" : normalizedCharacter = "s";
				   break;	
		case "â" : normalizedCharacter = "a";
				   break;					
		case "Ș" : normalizedCharacter = "S";
				   break;	
		case "Ț" : normalizedCharacter = "T";
				   break;				
		case "Î" : normalizedCharacter = "I";
				   break;
		case "Â" : normalizedCharacter = "A";
				   break;				
		case "Ă" : normalizedCharacter = "A";
			       break;
		}
				
		return normalizedCharacter;

	}
	
	public String removeDiacritics(String title) throws UnsupportedEncodingException {
		String titleWithRemovedDiacritics = new String();
		
		for(int i=0; i<title.length(); i++) {
			char c = title.charAt(i);
			String character = Character.toString(c);
			String characterToBeAdded = new String();
			if(characterIsDiacritic(character)) {
				characterToBeAdded = normalizeCharacter(character);
			} else {
			  characterToBeAdded = character;
			}
			titleWithRemovedDiacritics = titleWithRemovedDiacritics + characterToBeAdded;					
		}
		
		return titleWithRemovedDiacritics;
		
	}

}
