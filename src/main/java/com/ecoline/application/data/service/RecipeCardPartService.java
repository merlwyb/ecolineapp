package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.RecipeCardPart;
import com.ecoline.application.data.entity.RecipePart;
import com.ecoline.application.data.repository.RecipeCardPartRepository;
import com.ecoline.application.data.repository.RecipePartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCardPartService {

    private RecipeCardPartRepository recipeCardPartRepository;

    public RecipeCardPartService(@Autowired RecipeCardPartRepository recipeCardPartRepository) {
        this.recipeCardPartRepository = recipeCardPartRepository;
    }

    public Optional<RecipeCardPart> get(Long id) {
        return recipeCardPartRepository.findById(id);
    }

    public RecipeCardPart update(RecipeCardPart entity) {
        return recipeCardPartRepository.save(entity);
    }

    public void delete(Long id) {
        recipeCardPartRepository.deleteById(id);
    }

    public Page<RecipeCardPart> list(Pageable pageable) {
        return recipeCardPartRepository.findAll(pageable);
    }

    public int count() {
        return (int) recipeCardPartRepository.count();
    }

    public List<RecipeCardPart> getAll() {
        return recipeCardPartRepository.findAll();
    }

}
