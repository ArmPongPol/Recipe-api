package com.example.recipeApi.recipe.controller

import com.example.recipeApi.app.response.HttpResponse
import com.example.recipeApi.recipe.request.RecipeRequest
import com.example.recipeApi.recipe.service.RecipeService
import com.example.recipeApi.util.CallExternalApi
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/recipes")
class RecipeController @Autowired constructor(
  private val recipeService: RecipeService,
  private val callExternalApi: CallExternalApi
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

  @GetMapping("/page")
  fun getAllRecipes(
    @RequestParam(name = "page", defaultValue = "0") page: Int,
    @RequestParam(name = "size", defaultValue = "10") size: Int,
    @RequestParam(name = "search", required = false) search: String?
  ): ResponseEntity<Any> {
    return try {
      val pageable = PageRequest.of(page, size)
      ResponseEntity.ok(recipeService.getAllRecipes(pageable, search))
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error")
    }
  }

  @PostMapping("/check-slip")
  fun checkSlip(
    @RequestPart file: MultipartFile
  ): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(
        HttpResponse(
          true,
          "Slip submitted successfully",
          callExternalApi.submitSlip(file)
        )
      )
    } catch (e: Exception) {
      logger.error(e.message)
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        HttpResponse(false, e.message ?: "Error")
      )
    }
  }
}