package com.ecoline.application.data.repository;

import com.ecoline.application.data.entity.ComponentPortion;
import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.entity.util.SumAndCountUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponentPortionRepository extends JpaRepository<ComponentPortion, Long> {

    @Query("select c from ComponentPortion c where c.order.id=:orderId")
    List<ComponentPortion> findByOrderId(@Param("orderId") Long orderId);


    //@Query(value="select sum(weight_actual), count(weight_actual) from component_portion where component_name=:compName and order_id=:orderId", nativeQuery=true )
    @Query("select new com.ecoline.application.data.entity.util.SumAndCountUtility(sum(cp.weightActual), count(cp.weightActual)) " +
            "from ComponentPortion cp where cp.componentName=:compName and cp.order.id=:orderId")
    SumAndCountUtility findSumAndCountForDeviation(@Param("compName") String componentName, @Param("orderId") Long orderId);

//    @Query("select new Integer(count distinct ) from ComponentPortion ")
//    Integer findDistinctAmountOfIsOnMixingMachine(@Param());
}
