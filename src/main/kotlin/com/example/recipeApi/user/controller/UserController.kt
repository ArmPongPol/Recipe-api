package com.example.recipeApi.user.controller

import com.example.recipeApi.user.request.UserSignInRequest
import com.example.recipeApi.user.request.UserSignUpRequest
import com.example.recipeApi.user.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor (
  private val userService: UserService
) {
  @PostMapping("/sign-up")
  fun signUp(
    @RequestBody request: UserSignUpRequest
  ): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(userService.signUp(request))
    } catch (e: Exception) {
      ResponseEntity.badRequest().body(e.message)
    }
  }

  @PostMapping("/sign-in")
  fun signIn(
    @RequestBody request: UserSignInRequest,
  ): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(userService.signIn(request))
    } catch (e: Exception) {
      ResponseEntity.badRequest().body(e.message)
    }
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/all")
  fun getAllUsers(): ResponseEntity<Any> {
    return try {
      ResponseEntity.ok(userService.getAllUsers())
    } catch (e: Exception) {
      ResponseEntity.badRequest().body(e.message)
    }
  }
}