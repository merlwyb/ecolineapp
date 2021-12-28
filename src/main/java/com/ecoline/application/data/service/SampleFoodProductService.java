package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.SampleFoodProduct;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SampleFoodProductService {

    private SampleFoodProductRepository repository;

    public SampleFoodProductService(@Autowired SampleFoodProductRepository repository) {
        this.repository = repository;
    }

    public Optional<SampleFoodProduct> get(Integer id) {
        return repository.findById(id);
    }

    public SampleFoodProduct update(SampleFoodProduct entity) {
        return repository.save(entity);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Page<SampleFoodProduct> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
