package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.LabJournal;
import com.ecoline.application.data.entity.LogJournal;
import com.ecoline.application.data.repository.LabJournalRepository;
import com.ecoline.application.data.repository.LogJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogJournalService {

    private LogJournalRepository logJournalRepository;

    public LogJournalService(@Autowired LogJournalRepository logJournalRepository) {
        this.logJournalRepository = logJournalRepository;
    }

    public Optional<LogJournal> get(Long id) {
        return logJournalRepository.findById(id);
    }

    public LogJournal update(LogJournal entity) {
        return logJournalRepository.save(entity);
    }

    public void delete(Long id) {
        logJournalRepository.deleteById(id);
    }

    public Page<LogJournal> list(Pageable pageable) {
        return logJournalRepository.findAll(pageable);
    }

    public List<LogJournal> getAll() {
        return logJournalRepository.findAll();
    }
}
