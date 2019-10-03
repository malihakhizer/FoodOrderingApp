package com.example.malihakhizer.foodorderingapp.Models;

import java.util.ArrayList;

public class Request {
    String phone_number;
    String name;
    String address;
    String total;
    ArrayList<Order> foods = new ArrayList<>();

    public Request(String phone_number, String name, String address, String total, ArrayList<Order> foods) {
        this.phone_number = phone_number;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Order> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Order> foods) {
        this.foods = foods;
    }
}
