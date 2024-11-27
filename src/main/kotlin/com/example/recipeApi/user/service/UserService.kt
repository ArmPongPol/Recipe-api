package com.example.recipeApi.user.service

import com.example.recipeApi.user.dto.UserDto
import com.example.recipeApi.user.dto.UserSignUpDto
import com.example.recipeApi.user.request.UpdateUserRequest
import com.example.recipeApi.user.request.UserSignInRequest
import com.example.recipeApi.user.request.UserSignUpRequest
import jakarta.servlet.http.HttpServletResponse

interface UserService {
  fun signUp(request: UserSignUpRequest): UserSignUpDto
  fun signIn(request: UserSignInRequest, response: HttpServletResponse): String
  fun getProfile(userId: Long): UserDto
  fun updateProfile(userId: Long, request: UpdateUserRequest): UserDto
}