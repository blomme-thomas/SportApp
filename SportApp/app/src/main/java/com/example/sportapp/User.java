package com.example.sportapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class User {

    public String username, age, email, id;
    public ArrayList<String> sports, levels;

    public User(){}

    public User(String username, String age, String email, String id, ArrayList<String> sports, ArrayList<String> levels) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.id = id;
        this.sports = sports;
        this.levels = levels;
    }

    @NonNull
    @Override
    public String toString() {
        return username;
    }
}
