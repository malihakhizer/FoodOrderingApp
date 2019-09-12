package com.example.malihakhizer.foodorderingapp.Models;

public class User {
    String name;
    String email;
    String password;
    String id;
    String url;

    public User(String name, String email, String password, String id, String url) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
