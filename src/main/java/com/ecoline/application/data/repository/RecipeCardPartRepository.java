package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.RecipeCardPart;
import com.ecoline.application.data.entity.RecipePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCardPartRepository extends JpaRepository<RecipeCardPart, Long> {

}
