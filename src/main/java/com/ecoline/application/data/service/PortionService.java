package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Portion;
import com.ecoline.application.data.repository.PortionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortionService {
    private PortionRepository portionRepository;

    public PortionService(@Autowired PortionRepository portionRepository) {
        this.portionRepository = portionRepository;
    }

    public Optional<Portion> get(Long id) {
        return portionRepository.findById(id);
    }

    public Portion update(Portion entity) {
        return portionRepository.save(entity);
    }

    public void delete(Long id) {
        portionRepository.deleteById(id);
    }

    public Page<Portion> list(Pageable pageable) {
        return portionRepository.findAll(pageable);
    }

    public List<Portion> listWithUsername(String username) {
        return portionRepository.findAllWithUsername(username);
    }

    public int count() {
        return (int) portionRepository.count();
    }

}
