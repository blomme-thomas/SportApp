package com.example.sportapp;

import com.google.firebase.database.Exclude;

public class Equipe {
    private String key;
    public String nom, niveau, description;
    public Equipe(){}

    public Equipe(String nom, String niveau, String description) {
        this.nom = nom;
        this.niveau = niveau;
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
