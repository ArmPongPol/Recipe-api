package com.example.recipeApi.recipe.service

import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.request.RecipeRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecipeService {
  fun createRecipe(request: RecipeRequest): RecipeDto
  fun getAllRecipes(pageable: Pageable, search: String?): Page<RecipeDto>
}