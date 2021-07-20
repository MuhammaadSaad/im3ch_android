package com.adnan.tech.im3ch.Model;

public class Mechanic {

    String name;
    String mechenicId;
    String location;
    String rating;
    String image;
    String Services;

    public Mechanic(String mechenicId,String name, String location, String rating, String image, String services) {
        this.name = name;
        this.mechenicId = mechenicId;
        this.location = location;
        this.rating = rating;
        this.image = image;
        Services = services;
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
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getServices() {
        return Services;
    }

    public void setServices(String Services) {
        this.Services = image;
    }
}
