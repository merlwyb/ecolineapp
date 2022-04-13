package com.ecoline.application.data.entity;


import javax.persistence.*;

//Рецептурная карта

@Entity
@Table(name = "TECHNOLOGICAL_CARD")
public class TechnologicalCard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long orderId;
    private String componentType;
    private String componentName;
    private int amountRemaining;
    private double weight;
    private double deviation;

    public TechnologicalCard() {
    }

    public TechnologicalCard(Long orderId, String componentType, String componentName, int amountRemaining, double weight, double deviation) {
        this.orderId = orderId;
        this.componentType = componentType;
        this.componentName = componentName;
        this.amountRemaining = amountRemaining;
        this.weight = weight;
        this.deviation = deviation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public int getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }

}
