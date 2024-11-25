package com.example.recipeApi.recipe.service.impl

import com.example.recipeApi.entity.Recipe
import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.repository.RecipeRepository
import com.example.recipeApi.recipe.request.RecipeRequest
import com.example.recipeApi.recipe.service.RecipeService
import com.example.recipeApi.user.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl @Autowired constructor(
  private val recipeRepository: RecipeRepository,
  private val userRepository: UserRepository,
): RecipeService {
  override fun createRecipe(request: RecipeRequest): RecipeDto {
    userRepository.findById(request.userId).orElseThrow {
      throw Exception("User not found")
    }

    val recipe = recipeRepository.save(
      Recipe(
        name = request.name,
        description = request.description,
        userId = request.userId
      )
    )

    return recipe.toRecipeDto()
  }
}