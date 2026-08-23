package com.github.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.recipe.app.services.RecipeService;

@Controller
public class IndexController {

	private RecipeService recipeService;

	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping({ "", "/", "/index", "/index.html" })
	public String getIndexPage(Model model) {
		model.addAttribute("recipes", recipeService.findAll());

		return "index";
	}
}
