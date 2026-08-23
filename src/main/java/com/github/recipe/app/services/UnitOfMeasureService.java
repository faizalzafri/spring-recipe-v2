package com.github.recipe.app.services;

import java.util.Set;

import com.github.recipe.app.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> findAll();

}
