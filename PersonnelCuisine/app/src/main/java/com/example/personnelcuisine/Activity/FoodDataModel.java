package com.example.personnelcuisine.Activity;

import java.util.ArrayList;

public class FoodDataModel {

    private long id;
    private String name;
    private int Price;
    private String Description;
    private String Type;

    private byte[] Image;
    int numberOrder;

    public FoodDataModel()
    {

    }

    public FoodDataModel(String name, int price, String description, String type, byte[] image) {
        this.name = name;
        Price = price;
        Description = description;
        Type = type;
        Image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public void setNumberInCart(int numberOrder) {
 this.numberOrder=numberOrder;
    }
    public int getNumberInCart() {
        return numberOrder;
    }


}
