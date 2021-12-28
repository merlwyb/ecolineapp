package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.SampleFoodProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleFoodProductRepository extends JpaRepository<SampleFoodProduct, Integer> {

}