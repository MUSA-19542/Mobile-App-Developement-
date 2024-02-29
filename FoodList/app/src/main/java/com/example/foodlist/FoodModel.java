package com.example.foodlist;

public class FoodModel {
    private long id;
    private String name;
    private String description;
    private String number;
    private String location;
    private String price;
    private String imageUri;

    public FoodModel(long id, String name, String description, String number, String location, String price, String imageUri) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
        this.location = location;
        this.price = price;
        this.imageUri = imageUri;
    }

    // Getters and setters for all fields

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
