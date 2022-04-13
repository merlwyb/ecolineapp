package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r where r.recipeStringIdentifier=:recipeStringIdentifier")
    Recipe findByRecipeStringIdentifier(@Param("recipeStringIdentifier") String recipeStringIdentifier);

}
