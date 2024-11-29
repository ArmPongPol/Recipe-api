package com.example.recipeApi.util

import com.example.recipeApi.response.RatingNotificationMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class NotificationUtil @Autowired constructor(
  private val messagingTemplate: SimpMessagingTemplate
) {
  fun ratingNotification(userId: Long, rating: Int, recipeId: Long) {
    val notification = RatingNotificationMessage(
      userId = userId,
      rating = rating,
      recipeId = recipeId
    )

    messagingTemplate.convertAndSend("/topic/rating/$recipeId", notification)
  }
}