package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.ComponentPortion;
import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.entity.util.SumAndCountUtility;
import com.ecoline.application.data.repository.ComponentPortionRepository;
import com.ecoline.application.data.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentPortionService {

    private ComponentPortionRepository componentPortionRepository;

    public ComponentPortionService(@Autowired ComponentPortionRepository componentPortionRepository) {
        this.componentPortionRepository = componentPortionRepository;
    }

    public Optional<ComponentPortion> get(Long id) {
        return componentPortionRepository.findById(id);
    }

    public ComponentPortion update(ComponentPortion entity) {
        return componentPortionRepository.save(entity);
    }

    public void delete(Long id) {
        componentPortionRepository.deleteById(id);
    }

    public Page<ComponentPortion> list(Pageable pageable) {
        return componentPortionRepository.findAll(pageable);
    }

    public int count() {
        return (int) componentPortionRepository.count();
    }

    public List<ComponentPortion> getAll() {
        return componentPortionRepository.findAll();
    }

    public List<ComponentPortion> getByOrderId(Long orderId) {
        return componentPortionRepository.findByOrderId(orderId);
    }

    public SumAndCountUtility getSumAndCountForDeviation(String componentName, Long orderId) {
        return componentPortionRepository.findSumAndCountForDeviation(componentName, orderId);
    }
}
