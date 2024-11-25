package com.example.recipeApi.recipe.service

import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.request.RecipeRequest

interface RecipeService {
  fun createRecipe(request: RecipeRequest): RecipeDto
}