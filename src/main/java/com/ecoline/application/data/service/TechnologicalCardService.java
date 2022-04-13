package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.TechnologicalCard;
import com.ecoline.application.data.repository.TechnologicalCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnologicalCardService {

    private TechnologicalCardRepository technologicalCardRepository;

    public TechnologicalCardService(@Autowired TechnologicalCardRepository technologicalCardRepository) {
        this.technologicalCardRepository = technologicalCardRepository;
    }

    public Optional<TechnologicalCard> get(Long id) {
        return technologicalCardRepository.findById(id);
    }

    public TechnologicalCard update(TechnologicalCard entity) {
        return technologicalCardRepository.save(entity);
    }

    public void delete(Long id) {
        technologicalCardRepository.deleteById(id);
    }

    public Page<TechnologicalCard> list(Pageable pageable) {
        return technologicalCardRepository.findAll(pageable);
    }

    public int count() {
        return (int) technologicalCardRepository.count();
    }

    public List<TechnologicalCard> getAll() {
        return technologicalCardRepository.findAll();
    }

    public List<TechnologicalCard> getAllWhereRubber() {
        return technologicalCardRepository.findAllWhereRubber();
    }
    public List<TechnologicalCard> getAllWhereChalk() {
        return technologicalCardRepository.findAllWhereChalk();
    }
    public List<TechnologicalCard> getAllWhereBulk() {
        return technologicalCardRepository.findAllWhereBulk();
    }
    public List<TechnologicalCard> getAllWhereCarbon() {
        return technologicalCardRepository.findAllWhereCarbon();
    }

}
