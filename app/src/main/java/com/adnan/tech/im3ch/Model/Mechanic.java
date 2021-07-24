package com.adnan.tech.im3ch.Model;

public class Mechanic {

    String name;
    String mechenicId;
    String location;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    String phone;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDent_type() {
        return dent_type;
    }

    public void setDent_type(String dent_type) {
        this.dent_type = dent_type;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    String price;
    String dent_type;
    String rating;
    String pics;
    String description;

    public Mechanic(String mechenicId,String price,String dent_type,String name, String location, String rating, String image, String services) {
        this.name = name;
        this.price = price;
        this.dent_type = dent_type;
        this.mechenicId = mechenicId;
        this.location = location;
        this.rating = rating;
        this.pics = image;
        description = services;
    }

    public Mechanic()
    {


    }

    public String getMechenicId() {
        return mechenicId;
    }

    public void setMechenicId(String mechenicId) {
        this.mechenicId = mechenicId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return pics;
    }

    public void setImage(String image) {
        this.pics = image;
    }
    public String getServices() {
        return description;
    }

    public void setServices(String Services) {
        this.description = Services;
    }
}
