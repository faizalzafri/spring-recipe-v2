package com.github.recipe.app.services;

import java.util.Set;

import com.github.recipe.app.commands.RecipeCommand;
import com.github.recipe.app.domain.Recipe;

public interface RecipeService {

	Set<Recipe> findAll();

	Recipe findById(Long id);

	RecipeCommand findCommandById(Long id);

	RecipeCommand save(RecipeCommand command);

	void deleteById(Long id);
}
