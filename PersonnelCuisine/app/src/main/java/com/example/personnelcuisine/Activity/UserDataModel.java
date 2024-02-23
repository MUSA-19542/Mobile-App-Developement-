package com.example.personnelcuisine.Activity;

public class UserDataModel {
    private long id;
    private String name;
    private String email;
    private String address;
    private String password;

    private byte[] image;

    public UserDataModel() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDataModel(String name, String email, String address, String password, byte[] image) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.image = image;
        this.password=password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
