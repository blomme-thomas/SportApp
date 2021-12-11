package com.example.sportapp;

import java.util.ArrayList;

public class User {

    public String username, age, email;
    public ArrayList<String> sports, levels;

    public User(){}

    public User(String username, String age, String email) {
        this.username = username;
        this.age = age;
        this.email = email;
        this.sports = new ArrayList<>();
        this.levels = new ArrayList<>();
    }
}
