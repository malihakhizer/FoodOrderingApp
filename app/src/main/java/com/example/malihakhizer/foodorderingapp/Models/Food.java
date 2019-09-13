package com.example.malihakhizer.foodorderingapp.Models;

public class Food {
    String name;
    String description;
    String discount;
    String image;
    String menuId;
    String price;

    public Food() {
    }

    public Food(String name, String description, String discount, String image, String menuId, String price) {
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.image = image;
        this.menuId = menuId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDiscount() {
        return discount;
    }

    public String getImage() {
        return image;
    }

    public String getMenuId() {
        return menuId;
    }

    public String getPrice() {
        return price;
    }
}

