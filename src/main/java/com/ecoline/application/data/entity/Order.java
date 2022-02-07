package com.ecoline.application.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    private Long id;

    private Long rubberId = 0L;
    private Long bulkId = 0L;
    private Long chalkId = 0L;
    private Long carbonId = 0L;

    private double rubberCorrectedWeight = 0;
    private double bulkCorrectedWeight = 0;
    private double chalkCorrectedWeight = 0;
    private double carbonCorrectedWeight = 0;

    private String respUsername = "";
    private boolean isCorrected = false;

    private boolean isMixed = false;
    private String respUsernameMixing = "";
    private boolean isRolled = false;
    private String respUsernameRolling = "";
    private boolean isDried = false;
    private String respUsernameDrying = "";
    private boolean isSelected = false;
    private String respUsernameSelecting = "";

    public Order() {
    }

    public void setId(Long orderId) {
        this.id = orderId;
    }

    public Long getId() {
        return id;
    }

    public Long getRubberId() {
        return rubberId;
    }

    public void setRubberId(Long rubberId) {
        this.rubberId = rubberId;
    }

    public Long getBulkId() {
        return bulkId;
    }

    public void setBulkId(Long bulkId) {
        this.bulkId = bulkId;
    }

    public Long getChalkId() {
        return chalkId;
    }

    public void setChalkId(Long chalkId) {
        this.chalkId = chalkId;
    }

    public Long getCarbonId() {
        return carbonId;
    }

    public void setCarbonId(Long carbonId) {
        this.carbonId = carbonId;
    }

    public double getRubberCorrectedWeight() {
        return rubberCorrectedWeight;
    }

    public void setRubberCorrectedWeight(double rubberCorrectedWeight) {
        this.rubberCorrectedWeight = rubberCorrectedWeight;
    }

    public double getBulkCorrectedWeight() {
        return bulkCorrectedWeight;
    }

    public void setBulkCorrectedWeight(double bulkCorrectedWeight) {
        this.bulkCorrectedWeight = bulkCorrectedWeight;
    }

    public double getChalkCorrectedWeight() {
        return chalkCorrectedWeight;
    }

    public void setChalkCorrectedWeight(double chalkCorrectedWeight) {
        this.chalkCorrectedWeight = chalkCorrectedWeight;
    }

    public double getCarbonCorrectedWeight() {
        return carbonCorrectedWeight;
    }

    public void setCarbonCorrectedWeight(double carbonCorrectedWeight) {
        this.carbonCorrectedWeight = carbonCorrectedWeight;
    }

    public String getRespUsername() {
        return respUsername;
    }

    public void setRespUsername(String respUsername) {
        this.respUsername = respUsername;
    }

    public boolean isCorrected() {
        return isCorrected;
    }

    public void setCorrected(boolean corrected) {
        isCorrected = corrected;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getRespUsernameMixing() {
        return respUsernameMixing;
    }

    public void setRespUsernameMixing(String respUsernameMixing) {
        this.respUsernameMixing = respUsernameMixing;
    }

    public String getRespUsernameRolling() {
        return respUsernameRolling;
    }

    public void setRespUsernameRolling(String respUsernameRolling) {
        this.respUsernameRolling = respUsernameRolling;
    }

    public String getRespUsernameSelecting() {
        return respUsernameSelecting;
    }

    public void setRespUsernameSelecting(String respUsernameSelecting) {
        this.respUsernameSelecting = respUsernameSelecting;
    }

    public boolean isDried() {
        return isDried;
    }

    public void setDried(boolean dried) {
        isDried = dried;
    }

    public String getRespUsernameDrying() {
        return respUsernameDrying;
    }

    public void setRespUsernameDrying(String respUsernameDrying) {
        this.respUsernameDrying = respUsernameDrying;
    }
}
