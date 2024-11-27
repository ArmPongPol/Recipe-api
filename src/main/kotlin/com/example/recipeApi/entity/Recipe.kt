package com.example.recipeApi.entity

import com.example.recipeApi.recipe.dto.RecipeDto
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "recipe")
data class Recipe(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long = 0,
  val name: String,
  val description: String? = null,
  val imageUrl: String? = null,
  val userId: Long,
  val isPublished: Boolean = false,
  @CreationTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  val createDate: Date = Date(),
  @UpdateTimestamp
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  val modifiedDate: Date = Date(),

  @ManyToOne
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  val user: User? = null,

) {
  fun toRecipeDto(): RecipeDto {
    return RecipeDto(
      id = id,
      name = name,
      description = description,
      imageUrl = imageUrl,
      user = user?.toUserDto(),
      isPublished = isPublished,
      createDate = createDate,
      modifiedDate = modifiedDate
    )
  }
}
