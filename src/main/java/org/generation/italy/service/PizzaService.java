package org.generation.italy.service;

import java.util.List;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.model.Pizza;
import org.generation.italy.repository.IngredientiRepository;
import org.generation.italy.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository repository;
	
	@Autowired
	private IngredientiRepository ingredientiRepository;
	
	public List<Pizza> findAllSortedByName() {
		return repository.findAll(Sort.by("nome"));
	}
	
	public Pizza save(Pizza pizza) {
		return repository.save(pizza);
	}
	
	public Ingredienti addIngredient(Ingredienti ingredienti) {
		return ingredientiRepository.save(ingredienti);
	}
	
	public List<Ingredienti> findAllIngredientiSortedByName(){
		return ingredientiRepository.findAll(Sort.by("nome"));
	}

}
