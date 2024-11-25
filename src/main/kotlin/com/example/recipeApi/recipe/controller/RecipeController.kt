package com.example.recipeApi.recipe.controller

import com.example.recipeApi.recipe.request.RecipeRequest
import com.example.recipeApi.recipe.service.RecipeService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}