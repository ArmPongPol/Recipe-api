package com.example.recipeApi.app.errorhandle

import com.example.recipeApi.app.response.ErrorResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {

  @ExceptionHandler(value = [(BadRequestException::class)])
  protected fun badRequestException(
    ex: BadRequestException
  ): ResponseEntity<Any> {
    val apiError = ErrorResponseMessage(message = ex.message ?: "Bad Request")
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError)
  }
}