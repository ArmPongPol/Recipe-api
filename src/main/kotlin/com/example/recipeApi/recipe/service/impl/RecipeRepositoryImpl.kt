package com.example.recipeApi.recipe.service.impl

import com.example.recipeApi.entity.QRecipe
import com.example.recipeApi.recipe.dto.QRecipeDto
import com.example.recipeApi.recipe.dto.RecipeDto
import com.example.recipeApi.recipe.repository.RecipeRepositoryCustom
import com.example.recipeApi.user.dto.QUserDto
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.annotation.PostConstruct
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class RecipeRepositoryImpl: RecipeRepositoryCustom {

  @PersistenceContext
  lateinit var entityManager: EntityManager
  lateinit var queryFactory: JPAQueryFactory

  @PostConstruct
  fun init() {
    queryFactory = JPAQueryFactory(entityManager)
  }

  private val qRecipe = QRecipe.recipe

  override fun getAllRecipes(pageable: Pageable, search: String?): Page<RecipeDto> {
    val criteria = qRecipe.id.isNotNull

    if (search != null) {
      criteria.and(qRecipe.name.containsIgnoreCase(search))
    }

    val query = queryFactory
      .select(
        QRecipeDto(
          qRecipe.id,
          qRecipe.name,
          qRecipe.description,
          qRecipe.imageUrl,
          QUserDto(
            qRecipe.user.id,
            qRecipe.user.name,
            qRecipe.user.username,
            qRecipe.user.email,
          ),
          qRecipe.createDate,
          qRecipe.modifiedDate
        )
      ).from(qRecipe)
      .where(criteria)
      .offset(pageable.offset)
      .limit(pageable.pageSize.toLong())
      .fetch()

    val totalQuery = queryFactory
      .select(qRecipe)
      .from(qRecipe)
      .where(criteria)
      .fetchCount()

    return PageImpl(query, pageable, totalQuery)
  }
}