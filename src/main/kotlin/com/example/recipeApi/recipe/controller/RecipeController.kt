package com.example.recipeApi.recipe.controller

import com.example.recipeApi.entity.User
import com.example.recipeApi.recipe.request.RecipeRatingRequest
import com.example.recipeApi.recipe.request.RecipeRequest
import com.example.recipeApi.recipe.service.RecipeService
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController @Autowired constructor(
  private val recipeService: RecipeService
) {
  val logger: Logger = LoggerFactory.getLogger(RecipeController::class.java)

  @PostMapping
  fun createRecipe(
    @RequestBody request: RecipeRequest
  ): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(recipeService.createRecipe(request))
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error")
    }
  }

  @GetMapping("/{id}")
  fun getRecipeById(
    @PathVariable id: Long
  ): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(recipeService.getRecipeById(id))
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error")
    }
  }

  @GetMapping("/page")
  fun getRecipes(
    @RequestParam("page", defaultValue = "0") page: Int,
    @RequestParam("size", defaultValue = "10") size: Int,
    @RequestParam("search", required = false) search: String?,
  ): ResponseEntity<Any> {
    return try {
      val pageable = PageRequest.of(page, size)
      ResponseEntity.ok(recipeService.getAllPublishedRecipes(pageable, search))
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error")
    }
  }

  @PostMapping("/rating/{recipeId}")
  fun ratingRecipe(
    @PathVariable recipeId: Long,
    @RequestBody request: RecipeRatingRequest,
  ): ResponseEntity<Any> {
    return try {
      recipeService.ratingRecipe(request, recipeId)
      ResponseEntity.ok("Rating success")
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }
  }
}