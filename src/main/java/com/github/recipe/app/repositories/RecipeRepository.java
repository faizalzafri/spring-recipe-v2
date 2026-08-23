package com.github.recipe.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.github.recipe.app.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
