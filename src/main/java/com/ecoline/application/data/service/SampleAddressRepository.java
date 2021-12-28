package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.SampleAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleAddressRepository extends JpaRepository<SampleAddress, Integer> {

}