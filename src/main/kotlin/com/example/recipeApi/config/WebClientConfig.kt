package com.example.recipeApi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

  @Value("\${splipok.api.url}")
  private lateinit var url: String

  @Value("\${splipok.api.key}")
  private lateinit var apiKey: String

  @Bean
  fun webClient(): WebClient {
    return WebClient.builder()
      .baseUrl(url)
      .defaultHeader("x-authorization", apiKey)
      .build()
  }
}