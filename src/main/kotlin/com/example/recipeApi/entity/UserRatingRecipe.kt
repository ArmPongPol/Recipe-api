package com.example.recipeApi.entity

import jakarta.persistence.*

@Entity
@Table(name = "user_rating_recipe")
data class UserRatingRecipe (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "user_id")
    val userId: Long = 0,
    @Column(name = "recipe_id")
    val recipeId: Long = 0,
    var rating: Int = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    val user: User? = null,

    @ManyToOne
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    val recipe: Recipe? = null,
)