package com.ecoline.application.data.service;

import com.ecoline.application.data.entity.Order;
import com.ecoline.application.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    public OrderService(@Autowired OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> get(Long id) {
        return orderRepository.findById(id);
    }

    public Order update(Order entity) {
        return orderRepository.save(entity);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    public Page<Order> list(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public int count() {
        return (int) orderRepository.count();
    }

    @Transactional(readOnly = true)
    public List<Long> getIdsWithoutComponent(String component) {
        switch (component) {
            case "Каучук":
                return orderRepository.findIdsWithoutRubber();
            case "Сыпучая смесь":
                return orderRepository.findIdsWithoutBulk();
            case "Мел":
                return orderRepository.findIdsWithoutChalk();
        }
        return orderRepository.findIdsWithoutCarbon();
    }

    ;

    @Transactional(readOnly = true)
    public void updateComponentId(String component, Long orderId, Long portionId, double portionWeight) {
        switch (component) {
            case "Каучук":
                orderRepository.putRubberIdAndWeight(orderId, portionId, portionWeight);
                return;
            case "Сыпучая смесь":
                orderRepository.putBulkIdAndWeight(orderId, portionId, portionWeight);
                return;
            case "Мел":
                orderRepository.putChalkIdAndWeight(orderId, portionId, portionWeight);
                return;
        }
        orderRepository.putCarbonIdAndWeight(orderId, portionId, portionWeight);
    }

    ;

    public List<Order> getAllWhereIsNotCorrected() {
        return orderRepository.findAllWhereIsNotCorrected();
    }

    public List<Order> getAllWhereIsNotMixed() {
        return orderRepository.findAllWhereIsNotMixed();
    }

    public List<Order> getAllWhereIsNotRolled() {
        return orderRepository.findAllWhereIsNotRolled();
    }

    public List<Order> getAllWhereIsNotSelected() {
        return orderRepository.findAllWhereIsNotSelected();
    }

    public List<Order> getAllWhereIsNotDried() {return orderRepository.findAllWhereIsNotDried();
    }
}
