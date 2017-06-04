package com.aii.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aii.platform.models.CarteCompleta;
import com.aii.platform.repository.CarteCompletaRepository;

@Service
public class CarteCompletaService {
	
	@Autowired
	private CarteCompletaRepository carteCompletaRepository;
	
	public CarteCompleta saveCarteCompleta(CarteCompleta carteCompleta) {
		return carteCompletaRepository.save(carteCompleta);
	}
	
	public CarteCompleta getCarteCompletaById(Long id) {
		return carteCompletaRepository.findOne(id);
	}
	
	public List<CarteCompleta> getCarteCompletaByYear(int year) {
		return carteCompletaRepository.findByAnPublicare(year);
	}
	
	

}
