package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.RecipePart;
import com.ecoline.application.data.repository.RecipePartRepository;
import com.ecoline.application.data.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipePartService {

    private RecipePartRepository recipePartRepository;

    public RecipePartService(@Autowired RecipePartRepository recipePartRepository) {
        this.recipePartRepository = recipePartRepository;
    }

    public Optional<RecipePart> get(Long id) {
        return recipePartRepository.findById(id);
    }

    public RecipePart update(RecipePart entity) {
        return recipePartRepository.save(entity);
    }

    public void delete(Long id) {
        recipePartRepository.deleteById(id);
    }

    public Page<RecipePart> list(Pageable pageable) {
        return recipePartRepository.findAll(pageable);
    }

    public int count() {
        return (int) recipePartRepository.count();
    }

    public List<RecipePart> getAll() {
        return recipePartRepository.findAll();
    }

}
