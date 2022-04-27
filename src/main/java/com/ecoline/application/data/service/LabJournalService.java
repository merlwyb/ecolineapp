package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.LabJournal;
import com.ecoline.application.data.entity.Recipe;
import com.ecoline.application.data.repository.LabJournalRepository;
import com.ecoline.application.data.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabJournalService {

    private LabJournalRepository labJournalRepository;

    public LabJournalService(@Autowired LabJournalRepository labJournalRepository) {
        this.labJournalRepository = labJournalRepository;
    }

    public Optional<LabJournal> get(Long id) {
        return labJournalRepository.findById(id);
    }

    public LabJournal update(LabJournal entity) {
        return labJournalRepository.save(entity);
    }

    public void delete(Long id) {
        labJournalRepository.deleteById(id);
    }

    public Page<LabJournal> list(Pageable pageable) {
        return labJournalRepository.findAll(pageable);
    }

    public int count() {
        return (int) labJournalRepository.count();
    }

    public List<LabJournal> getAll() {
        return labJournalRepository.findAll();
    }

    public List<LabJournal> getAllByStringIdentifier(String stringIdentifier) {
        return labJournalRepository.findAllByStringIdentifier(stringIdentifier);
    }
}
