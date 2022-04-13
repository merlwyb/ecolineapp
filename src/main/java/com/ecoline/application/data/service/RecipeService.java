package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.repository.OrderRepository;
import com.ecoline.application.data.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(@Autowired RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> get(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe update(Recipe entity) {
        return recipeRepository.save(entity);
    }

    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    public Page<Recipe> list(Pageable pageable) {
        return recipeRepository.findAll(pageable);
    }

    public int count() {
        return (int) recipeRepository.count();
    }

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public Recipe getByRecipeStringIdentifier(String recipeStringIdentifier){return recipeRepository.findByRecipeStringIdentifier(recipeStringIdentifier);}
}
