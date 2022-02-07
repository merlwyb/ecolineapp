package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Portion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortionRepository extends JpaRepository<Portion, Long> {

    //@Query("select distinct month(a.entranceTime)  as monthValue from Attendance a where year(a.entranceTime)=:year")
    @Query("select p from Portion p where p.respUsername=:username")
    List<Portion> findAllWithUsername(@Param("username") String username);

//    @Query("select p.weight from Portion p where p.portion_id=:portionId")
//    double findWeightById(@Param("portionId") Long portionId);
}
