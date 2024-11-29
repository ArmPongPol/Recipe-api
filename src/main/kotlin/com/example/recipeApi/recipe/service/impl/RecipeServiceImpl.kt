package com.example.recipeApi.recipe.service.impl

import com.example.recipeApi.entity.Recipe
import com.example.recipeApi.entity.User
import com.example.recipeApi.entity.UserRatingRecipe
import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.repository.RecipeRepository
import com.example.recipeApi.recipe.repository.UserRatingRecipeRepository
import com.example.recipeApi.recipe.request.RecipeRatingRequest
import com.example.recipeApi.recipe.request.RecipeRequest
import com.example.recipeApi.recipe.service.RecipeService
import com.example.recipeApi.user.repository.UserRepository
import com.example.recipeApi.util.NotificationUtil
import jakarta.transaction.Transactional
import org.apache.coyote.BadRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl @Autowired constructor(
  private val recipeRepository: RecipeRepository,
  private val userRepository: UserRepository,
  private val userRatingRecipeRepository: UserRatingRecipeRepository,
  private val notificationUtil: NotificationUtil,
) : RecipeService {
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

  override fun getRecipeById(recipeId: Long): RecipeDto {
    val recipe = recipeRepository.findById(recipeId).orElseThrow {
      throw Exception("Recipe not found")
    }

    return recipe.toRecipeDto()
  }

  override fun getAllPublishedRecipes(pageable: Pageable, search: String?): Page<RecipeDto> {
    return recipeRepository.getAllPublishedRecipes(pageable, search)
  }

  @Transactional
  override fun ratingRecipe(request: RecipeRatingRequest, recipeId: Long): Boolean {
    userRepository.findById(request.userId).orElseThrow {
      throw Exception("User not found")
    }

    val recipe = recipeRepository.findById(recipeId).orElseThrow {
      throw Exception("Recipe not found")
    }

    userRatingRecipeRepository.save(
      UserRatingRecipe(
        userId = request.userId,
        recipeId = recipeId,
        rating = request.rating
      )
    )

    if (request.rating > 5) {
      throw BadRequestException("Invalid rating value")
    }

    val countRating = userRatingRecipeRepository.countByRecipeId(recipeId)
    val sumRating = userRatingRecipeRepository.sumRatingByRecipeId(recipeId)

    recipe.rating = sumRating / countRating

    notificationUtil.ratingNotification(request.userId, request.rating, recipeId)

    return true
  }


}