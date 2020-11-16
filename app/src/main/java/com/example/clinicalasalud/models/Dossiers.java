package com.example.clinicalasalud.models;

import java.io.Serializable;

public class Dossiers implements Serializable {
    private String date;
    private String time;
    private String name;
    private String age;
    private String height;
    private String weight;
    private String phone;
    private String sickness;
    private String history;
    private String backgroundRecord;
    private String physicalTest;
    private String diagnose;
    private String treatment;

    public Dossiers(String date, String time, String name, String age, String height, String weight, String phone, String sickness, String history, String backgroundRecord, String physicalTest, String diagnose, String treatment) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.phone = phone;
        this.sickness = sickness;
        this.history = history;
        this.backgroundRecord = backgroundRecord;
        this.physicalTest = physicalTest;
        this.diagnose = diagnose;
        this.treatment = treatment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getBackgroundRecord() {
        return backgroundRecord;
    }

    public void setBackgroundRecord(String backgroundRecord) {
        this.backgroundRecord = backgroundRecord;
    }

    public String getPhysicalTest() {
        return physicalTest;
    }

    public void setPhysicalTest(String physicalTest) {
        this.physicalTest = physicalTest;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Dossiers() {
    }

   }