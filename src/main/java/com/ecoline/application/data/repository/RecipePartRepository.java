package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.RecipePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipePartRepository extends JpaRepository<RecipePart, Long> {

}
