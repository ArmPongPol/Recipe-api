package com.example.recipeApi.util

import com.example.recipeApi.app.errorhandle.BadRequestException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.bodyToMono

@Service
class CallExternalApi @Autowired constructor(
  private val webClient: WebClient
) {
  fun submitSlip(file: MultipartFile): Map<String, Any> {
    val body: MultiValueMap<String, Any> = LinkedMultiValueMap()
    body.add("files", file.resource)

    return try {
      val response = webClient.post()
        .contentType(MediaType.MULTIPART_FORM_DATA)
        .bodyValue(body)
        .retrieve()
        .bodyToMono<Map<String, Any>>()
        .block()!!

      processResponse(response)
    } catch (ex: WebClientResponseException) {
      handleWebClientError(ex)

    } catch (ex: Exception) {
      // Handle other errors
      throw Exception("Unexpected error: ${ex.message}")
    }
  }


  private fun processResponse(response: Map<String, Any>): Map<String, Any> {
    // Check nested data success
    val data = response["data"] as? Map<String, Any>
      ?: throw RuntimeException("Invalid response: 'data' field missing")
    val success = data["success"] as? Boolean

    if (success == true) {
      return data // Return nested data if successful
    } else {
      val message = data["message"] as? String ?: "Unknown error in nested data"
      throw RuntimeException("External API nested error: $message")
    }
  }




  private fun handleWebClientError(ex: WebClientResponseException): Nothing {
    val errorDetails = parseErrorResponse(ex.responseBodyAsString)

    throw BadRequestException(errorDetails ?: "Bad Request")
  }

  private fun parseErrorResponse(responseBody: String): String? {
    // Try to extract a specific error message from the response
    return try {
      // Assuming the error response is a JSON object with a "message" field
      val errorMap: Map<*, *>? = ObjectMapper().readValue(responseBody, Map::class.java)
      errorMap?.get("message") as? String
    } catch (ex: Exception) {
      throw Exception("Internal Server Error.")
    }
  }
}