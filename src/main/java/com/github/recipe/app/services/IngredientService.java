package com.github.recipe.app.services;

import com.github.recipe.app.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long id);

	IngredientCommand save(IngredientCommand ingredientCommand);

	void deleteById(Long recipeId, Long id);
}
