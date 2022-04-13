package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.RecipeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeCardRepository extends JpaRepository<RecipeCard, Long> {
}
