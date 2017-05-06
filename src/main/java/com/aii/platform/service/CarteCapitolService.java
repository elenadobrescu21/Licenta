package com.aii.platform.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.CarteCapitol;
import com.aii.platform.repository.CarteCapitolRepository;

@Service
public class CarteCapitolService {
	
	@Autowired
	private CarteCapitolRepository carteCapitolRepository;
	
	
	public CarteCapitol saveCarteCapitol(CarteCapitol carteCapitol) {
		return carteCapitolRepository.save(carteCapitol);
	}
	
	public CarteCapitol getCarteCapitolById(Long id) {
		return carteCapitolRepository.findOne(id);
	}
	
	

}
