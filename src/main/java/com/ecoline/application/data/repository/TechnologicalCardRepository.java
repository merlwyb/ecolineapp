package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.TechnologicalCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechnologicalCardRepository extends JpaRepository<TechnologicalCard, Long> {

    @Query("select c from TechnologicalCard c where c.componentType='Каучук' and c.amountRemaining<>0")
    List<TechnologicalCard> findAllWhereRubber();

    @Query("select c from TechnologicalCard c where c.componentType='Мел' and c.amountRemaining<>0")
    List<TechnologicalCard> findAllWhereChalk();

    @Query("select c from TechnologicalCard c where c.componentType='Сыпучая смесь' and c.amountRemaining<>0")
    List<TechnologicalCard> findAllWhereBulk();

    @Query("select c from TechnologicalCard c where c.componentType='Техуглерод' and c.amountRemaining<>0")
    List<TechnologicalCard> findAllWhereCarbon();
}
