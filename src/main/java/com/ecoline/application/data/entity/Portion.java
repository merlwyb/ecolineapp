package com.ecoline.application.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "PORTION")
public class Portion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String component; //RUBBER, BULK, CHALK, CARBON
    private double weight = 0;
    private String respUsername = "";

    public Portion() {
    }

    public Portion(String component, double weight, String respUsername) {
        this.component = component;
        this.weight = weight;
        this.respUsername = respUsername;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getRespUsername() {
        return respUsername;
    }

    public void setRespUsername(String resp_username) {
        this.respUsername = resp_username;
    }
}
