package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.RecipeCard;
import com.ecoline.application.data.repository.RecipeCardRepository;
import com.ecoline.application.data.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCardService {

    private RecipeCardRepository recipeCardRepository;

    public RecipeCardService(@Autowired RecipeCardRepository recipeCardRepository) {
        this.recipeCardRepository = recipeCardRepository;
    }

    public Optional<RecipeCard> get(Long id) {
        return recipeCardRepository.findById(id);
    }

    public RecipeCard update(RecipeCard entity) {
        return recipeCardRepository.save(entity);
    }

    public void delete(Long id) {
        recipeCardRepository.deleteById(id);
    }

    public Page<RecipeCard> list(Pageable pageable) {
        return recipeCardRepository.findAll(pageable);
    }

    public int count() {
        return (int) recipeCardRepository.count();
    }

    public List<RecipeCard> getAll() {
        return recipeCardRepository.findAll();
    }

    //public double getWeightOfRecipeCard(){}
}
