package com.adnan.tech.im3ch.Model;

public class Customer {
    private String _id;

    private String service_type;

    private double lat;

    private double longitude;

    private String address;

    private String description;

    private String customerid;

    private String name;

    private String phone;

    private String Time;
    private String make;

    private String model;

    private int year;

    private int budget;

    private String dent_type;

    public void setMake(String make){
        this.make = make;
    }
    public String getMake(){
        return this.make;
    }
    public void setModel(String model){
        this.model = model;
    }
    public String getModel(){
        return this.model;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getYear(){
        return this.year;
    }
    public void setBudget(int budget){
        this.budget = budget;
    }
    public int getBudget(){
        return this.budget;
    }
    public void setDent_type(String dent_type){
        this.dent_type = dent_type;
    }
    public String getDent_type(){
        return this.dent_type;
    }
    public void set_id(String _id){
        this._id = _id;
    }
    public String get_id(){
        return this._id;
    }
    public void setService_type(String service_type){
        this.service_type = service_type;
    }
    public String getService_type(){
        return this.service_type;
    }
    public void setLat(double lat){
        this.lat = lat;
    }
    public double getLat(){
        return this.lat;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public double getLongitude(){
        return this.longitude;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setCustomerid(String customerid){
        this.customerid = customerid;
    }
    public String getCustomerid(){
        return this.customerid;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setTime(String Time){
        this.Time = Time;
    }
    public String getTime(){
        return this.Time;
    }
}
