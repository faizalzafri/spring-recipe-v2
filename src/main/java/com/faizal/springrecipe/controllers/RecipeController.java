package com.faizal.springrecipe.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.faizal.springrecipe.commands.RecipeCommand;
import com.faizal.springrecipe.domain.Recipe;
import com.faizal.springrecipe.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping(value = "/recipe/{id}/show")
	public String showRecipe(@PathVariable("id") String id, Model model) {
		Recipe recipe = recipeService.findById(Long.parseLong(id));
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}

	@GetMapping(value = "/recipe/new")
	public String addRecipeForm(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping(value = "/recipe/{id}/update")
	public String updateRecipeForm(@PathVariable("id") String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(id)));
		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping(value = "/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable("id") String id) {
		log.debug("Deleting recipe with id: {}", id);
		recipeService.deleteById(Long.parseLong(id));
		return "redirect:/";
	}

	@PostMapping("/recipe/save")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug("Validation error: {}", objectError.toString());
			});
			return RECIPE_RECIPEFORM_URL;
		}

		RecipeCommand savedCommand = recipeService.save(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
}
