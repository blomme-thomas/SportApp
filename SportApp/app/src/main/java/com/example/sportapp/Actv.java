package com.example.sportapp;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Actv {

    private String key;
    public String sport, lieu, description;

    public Actv(){}

    public Actv(String sport, String lieu, String description) {
        this.sport = sport;
        this.lieu = lieu;

        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
