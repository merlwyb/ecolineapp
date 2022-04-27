package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {


    @Query("select o from Order o where o.isRecipeSelected=true")
    List<Order> findAllWhereIsRecipeSelected();

    @Query("select o from Order o where o.isSend=false")
    List<Order> findAllWhereIsNotSend();

    @Query("select o from Order o where o.stringIdentifier=:stringIdentifier")
    Order findByStringIdentifier(@Param("stringIdentifier") String stringIdentifier);

    //@Query("select o from Order o where o.isWeighted=true")
    //@Query("select o from Order o inner join TechnologicalCard tc on o.id=tc.orderId where tc.amountRemaining=0")
    @Query(value="select orders.* from orders left outer join(select order_id, sum(amount_remaining) as sum from technological_card  group by order_id) " +
            "tc where orders.id=tc.order_id and tc.sum=0 and orders.is_mixed=false", nativeQuery=true )
    List<Order> findAllWhereIsWeighted();

//    @Modifying
//    @Query("update Order o set o.carbonId=:carbonId, o.carbonCorrectedWeight=:carbonWeight where o.id=:orderId")
//    void putCarbonIdAndWeight(@Param("orderId") Long orderId, @Param("carbonId") Long carbonId, @Param("carbonWeight") double carbonWeight);
//
//    @Query("select o from Order o where o.isCorrected=false")
//    List<Order> findAllWhereIsNotCorrected();
//
//    @Query("select o from Order o where o.isCorrected=true and o.isMixed=false")
//    List<Order> findAllWhereIsNotMixed();
//
    @Query("select o from Order o where o.isMixed=true and o.isRolled=false")
    List<Order> findAllWhereIsNotRolled();

    @Query("select o from Order o where o.isMixed=true and o.isRolled=true and o.isDried=false")
    List<Order> findAllWhereIsNotDried();

    @Query("select o from Order o where o.isMixed=true and o.isRolled=true and o.isDried=true")
    List<Order> findAllWhereIsCompleted();
//
//    @Query("select o from Order o where o.isCorrected=true and o.isMixed=true and o.isRolled=true and o.isDried=true and o.isSelected=false")
//    List<Order> findAllWhereIsNotSelected();
}
