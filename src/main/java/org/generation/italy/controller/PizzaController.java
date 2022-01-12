package org.generation.italy.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

	@Autowired
	private PizzaService service;
	
	@GetMapping
	public String list(Model model, @RequestParam(name="keyword", required=false) String keyword) {
		List<Pizza> result;
		if(keyword !=null && !keyword.isEmpty()) {
			result = service.findByKeywordSortedByRecent(keyword);
			model.addAttribute("keyword", keyword);
		}else {
			result = service.findAllSortedByName();
		}
		model.addAttribute("list", result);
		return "pizza/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("ingredientiList", service.findAllIngredientiSortedByName());
		return "/pizza/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult,Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
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
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("pizza", service.getById(id));
		model.addAttribute("ingredientiList", service.findAllIngredientiSortedByName());
		return "/pizza/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("ingredientiList", service.findAllIngredientiSortedByName());
			return "/pizza/edit";
		}
		service.save(formPizza);
		redirectAttributes.addFlashAttribute("successMessage", "Pizza edited");
		return "redirect:/pizza";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		service.deleteById(id);
		redirectAttributes.addFlashAttribute("successMessage", "Pizza deleted!");
		return "redirect:/pizza";
	}
}
