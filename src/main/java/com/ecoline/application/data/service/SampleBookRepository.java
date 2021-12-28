package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.SampleBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleBookRepository extends JpaRepository<SampleBook, Integer> {

}