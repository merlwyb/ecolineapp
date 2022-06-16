package com.ecoline.application.data.entity;

import javax.persistence.*;

//Данные о навеске
@Entity
@Table(name = "COMPONENT_PORTION")
public class ComponentPortion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String RFID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    private String componentType;
    private String componentName;
    private double weightActual;
    private double deviationActual;

    private boolean isOnMixingMachine = false;

    public ComponentPortion() {
    }

    public ComponentPortion(String componentType) {
        this.componentType = componentType;
    }

//    public ComponentPortion(String RFID, Order order, String componentType, String componentName, double weightActual, double deviationActual) {
//        this.RFID = RFID;
//        this.order = order;
//        this.componentType = componentType;
//        this.componentName = componentName;
//        this.weightActual = weightActual;
//        this.deviationActual = deviationActual;
//    }

    public Long getId() {
        return id;
    }

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public double getWeightActual() {
        return weightActual;
    }

    public void setWeightActual(double weightActual) {
        this.weightActual = weightActual;
    }

    public double getDeviationActual() {
        return deviationActual;
    }

    public void setDeviationActual(double deviationActual) {
        this.deviationActual = deviationActual;
    }

    public boolean isOnMixingMachine() {
        return isOnMixingMachine;
    }

    public void setOnMixingMachine(boolean onMixingMachine) {
        isOnMixingMachine = onMixingMachine;
    }
}
