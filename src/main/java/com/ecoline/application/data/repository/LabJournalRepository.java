package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.ComponentPortion;
import com.ecoline.application.data.entity.LabJournal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LabJournalRepository extends JpaRepository<LabJournal, Long> {

}
