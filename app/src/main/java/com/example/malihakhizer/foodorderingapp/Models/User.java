package com.example.malihakhizer.foodorderingapp.Models;

public class User {
    String name;
    String email;
    String password;
    String id;
    String url;
    String phone_number;
    String address;

    public User() {
    }

    public User(String name, String email, String password, String id, String url, String phone_number, String address) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
        this.url = url;
        this.phone_number = phone_number;
        this.address = address;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
