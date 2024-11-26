package com.example.recipeApi.user.service

import com.example.recipeApi.user.dto.UserDto
import com.example.recipeApi.user.dto.UserSignUpDto
import com.example.recipeApi.user.request.UserSignInRequest
import com.example.recipeApi.user.request.UserSignUpRequest

interface UserService {
  fun signUp(request: UserSignUpRequest): UserSignUpDto
  fun signIn(request: UserSignInRequest): String
  fun getAllUsers(): List<UserDto>
}