package com.ecoline.application.data.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "LAB_JOURNAL")
public class LabJournal { //todo ПРИ ВЫБОРЕ МАРКИ РЕЗ СМЕСИ АВТОМАТ ЗАПОЛНЯЕТСЯ ВУЛКАНИЗАЦИЯ НОРМА
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date = LocalDate.now(); //дата изготовления
    private String brand; //марка резинов. смеси
    private String numberLaying; // номер закладки
    //вулканизация
    private LocalDate vulcanizationDate; //дата
    private int vulcanizationTemperature; //температура
    private int vulcanizationTime; //время
    //твёрдость по Шор А, у.е.
    private int hardnessActual;
    private String hardnessIdeal;
    //Усл. прочность,н.м., мПа
    private double enduranceActual;
    private String enduranceIdeal;
    //Относит. Удлинение,  н.м., %
    private int lengtheningActual;
    private String lengtheningIdeal;
    //деформация
    private String deformationActual;
    //вылежка
    private String vylezhka;
    //Потребитель
    private String company;

    public LabJournal() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getNumberLaying() {
        return numberLaying;
    }

    public void setNumberLaying(String numberLaying) {
        this.numberLaying = numberLaying;
    }

    public LocalDate getVulcanizationDate() {
        return vulcanizationDate;
    }

    public void setVulcanizationDate(LocalDate vulcanizationDate) {
        this.vulcanizationDate = vulcanizationDate;
    }

    public int getVulcanizationTemperature() {
        return vulcanizationTemperature;
    }

    public void setVulcanizationTemperature(int vulcanizationTemperature) {
        this.vulcanizationTemperature = vulcanizationTemperature;
    }

    public int getVulcanizationTime() {
        return vulcanizationTime;
    }

    public void setVulcanizationTime(int vulcanizationTime) {
        this.vulcanizationTime = vulcanizationTime;
    }

    public int getHardnessActual() {
        return hardnessActual;
    }

    public void setHardnessActual(int hardnessActual) {
        this.hardnessActual = hardnessActual;
    }

    public String getHardnessIdeal() {
        return hardnessIdeal;
    }

    public void setHardnessIdeal(String hardnessIdeal) {
        this.hardnessIdeal = hardnessIdeal;
    }

    public double getEnduranceActual() {
        return enduranceActual;
    }

    public void setEnduranceActual(double enduranceActual) {
        this.enduranceActual = enduranceActual;
    }

    public String getEnduranceIdeal() {
        return enduranceIdeal;
    }

    public void setEnduranceIdeal(String enduranceIdeal) {
        this.enduranceIdeal = enduranceIdeal;
    }

    public int getLengtheningActual() {
        return lengtheningActual;
    }

    public void setLengtheningActual(int lengtheningActual) {
        this.lengtheningActual = lengtheningActual;
    }

    public String getLengtheningIdeal() {
        return lengtheningIdeal;
    }

    public void setLengtheningIdeal(String lengtheningIdeal) {
        this.lengtheningIdeal = lengtheningIdeal;
    }

    public String getDeformationActual() {
        return deformationActual;
    }

    public void setDeformationActual(String deformationActual) {
        this.deformationActual = deformationActual;
    }

    public String getVylezhka() {
        return vylezhka;
    }

    public void setVylezhka(String vylezhka) {
        this.vylezhka = vylezhka;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }


}
