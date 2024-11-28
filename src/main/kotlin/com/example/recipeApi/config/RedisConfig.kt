package com.example.recipeApi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig {
  @Value("\${spring.data.redis.host}")
  private lateinit var redisHost: String

  @Value("\${spring.data.redis.port}")
  private val redisPort: Int = 0

  @Bean
  fun jedisConnectionFactory(): JedisConnectionFactory {
    val configuration = RedisStandaloneConfiguration(redisHost, redisPort)
    return JedisConnectionFactory(configuration)
  }

  @Bean
  fun redisTemplate(): RedisTemplate<String, Any>? {
    val template = RedisTemplate<String, Any>()
    template.connectionFactory = jedisConnectionFactory()
    return template
  }
}