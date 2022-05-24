package com.ecoline.application.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String stringIdentifier = "";
    private int weightRequired = 1;
    private int amount = 0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RECIPECARD_ID")
    private RecipeCard recipeCard;

    private boolean isRecipeSelected = false;
    private boolean isSend = false;
    private boolean isMixed = false;
    private boolean isRolled = false;
    private int rollingTime = 0;
    private boolean isDried = false;
    private String company;

//    private String respUsername = "";
//    private boolean isCorrected = false;
//
//    private boolean isMixed = false;
//    private String respUsernameMixing = "";
//    private boolean isRolled = false;
//    private String respUsernameRolling = "";
//    private boolean isDried = false;
//    private String respUsernameDrying = "";
//    private boolean isSelected = false;
//    private String respUsernameSelecting = "";

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringIdentifier() {
        return stringIdentifier;
    }

    public void setStringIdentifier(String stringIdentifier) {
        this.stringIdentifier = stringIdentifier;
    }

    public int getWeightRequired() {
        return weightRequired;
    }

    public void setWeightRequired(int weightRequired) {
        this.weightRequired = weightRequired;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public RecipeCard getRecipeCard() {
        return recipeCard;
    }

    public void setRecipeCard(RecipeCard recipeCard) {
        this.recipeCard = recipeCard;
    }

//    public Set<ComponentPortion> getComponentPortions() {
//        return componentPortions;
//    }
//
//    public void setComponentPortions(Set<ComponentPortion> componentPortions) {
//        this.componentPortions = componentPortions;
//    }

    public boolean isRecipeSelected() {
        return isRecipeSelected;
    }

    public void setRecipeSelected(boolean recipeSelected) {
        isRecipeSelected = recipeSelected;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public boolean isMixed() {
        return isMixed;
    }

    public void setMixed(boolean mixed) {
        isMixed = mixed;
    }

    public boolean isRolled() {
        return isRolled;
    }

    public void setRolled(boolean rolled) {
        isRolled = rolled;
    }

    public int getRollingTime() {
        return rollingTime;
    }

    public void setRollingTime(int rollingTime) {
        this.rollingTime = rollingTime;
    }

    public boolean isDried() {
        return isDried;
    }

    public void setDried(boolean dried) {
        isDried = dried;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}


