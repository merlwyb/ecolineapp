package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    @Query("select o.id from Order o where o.rubberId=0L")
    List<Long> findIdsWithoutRubber();

    @Query("select o.id from Order o where o.bulkId=0L")
    List<Long> findIdsWithoutBulk();

    @Query("select o.id from Order o where o.chalkId=0L")
    List<Long> findIdsWithoutChalk();

    @Query("select o.id from Order o where o.carbonId=0L")
    List<Long> findIdsWithoutCarbon();

    @Modifying
    @Query("update Order o set o.rubberId=:rubberId, o.rubberCorrectedWeight=:rubberWeight where o.id=:orderId")
    void putRubberIdAndWeight(@Param("orderId") Long orderId, @Param("rubberId") Long rubberId, @Param("rubberWeight") double rubberWeight);

    @Modifying
    @Query("update Order o set o.bulkId=:bulkId, o.bulkCorrectedWeight=:bulkWeight where o.id=:orderId")
    void putBulkIdAndWeight(@Param("orderId") Long orderId, @Param("bulkId") Long bulkId, @Param("bulkWeight") double bulkWeight);

    @Modifying
    @Query("update Order o set o.chalkId=:chalkId, o.chalkCorrectedWeight=:chalkWeight where o.id=:orderId")
    void putChalkIdAndWeight(@Param("orderId") Long orderId, @Param("chalkId") Long chalkId, @Param("chalkWeight") double chalkWeight);

    @Modifying
    @Query("update Order o set o.carbonId=:carbonId, o.carbonCorrectedWeight=:carbonWeight where o.id=:orderId")
    void putCarbonIdAndWeight(@Param("orderId") Long orderId, @Param("carbonId") Long carbonId, @Param("carbonWeight") double carbonWeight);

    @Query("select o from Order o where o.isCorrected=false")
    List<Order> findAllWhereIsNotCorrected();

    @Query("select o from Order o where o.isCorrected=true and o.isMixed=false")
    List<Order> findAllWhereIsNotMixed();

    @Query("select o from Order o where o.isCorrected=true and o.isMixed=true and o.isRolled=false")
    List<Order> findAllWhereIsNotRolled();

    @Query("select o from Order o where o.isCorrected=true and o.isMixed=true and o.isRolled=true and o.isDried=false")
    List<Order> findAllWhereIsNotDried();

    @Query("select o from Order o where o.isCorrected=true and o.isMixed=true and o.isRolled=true and o.isDried=true and o.isSelected=false")
    List<Order> findAllWhereIsNotSelected();
}
