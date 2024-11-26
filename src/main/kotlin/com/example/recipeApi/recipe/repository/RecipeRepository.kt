package com.example.recipeApi.recipe.repository

import com.example.recipeApi.entity.Recipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeRepository: JpaRepository<Recipe, Long>, RecipeRepositoryCustom {
}