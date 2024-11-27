package com.example.recipeApi.recipe.repository

import com.example.recipeApi.recipe.dto.RecipeDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RecipeRepositoryCustom {
  fun getAllPublishedRecipes(pageable: Pageable, search: String?): Page<RecipeDto>
}