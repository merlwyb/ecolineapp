package com.ecoline.application.data.entity;


import javax.persistence.*;

//Рецептурная карта

@Entity
@Table(name = "RECIPE_PART")
public class RecipePart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String componentType;
    private String componentName;
    private double idealWeight;
    private double deviation;

    public RecipePart() {
    }

    public RecipePart(String componentType, String componentName, double idealWeight, double deviation) {
        this.componentType = componentType;
        this.componentName = componentName;
        this.idealWeight = idealWeight;
        this.deviation = deviation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(double idealWeight) {
        this.idealWeight = idealWeight;
    }

    public double getDeviation() {
        return deviation;
    }

    public void setDeviation(double deviation) {
        this.deviation = deviation;
    }
}
