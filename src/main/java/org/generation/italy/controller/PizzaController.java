package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.model.Pizza;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaService service;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllSortedByName());
		return "pizza/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredientiList", service.findAllIngredientiSortedByName());
		return "/pizza/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("ingredientiList", service.findAllIngredientiSortedByName());
			return "/pizza/edit";
		}
		service.save(formPizza);
		return "redirect:/pizza";
	}
	
	@GetMapping("/ingredients/add")
	public String addIngredient(Model model) {
		model.addAttribute("ingredienti", new Ingredienti());
		return "/pizza/add-ingredient";
	}
	
	@PostMapping("/ingredients/add")
	public String doAddIngredient(@Valid @ModelAttribute("ingredienti") Ingredienti formIngredienti, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "pizza/add-ingredient";
		}
		service.addIngredient(formIngredienti);
		return "redirect:/pizza";
	}
}
