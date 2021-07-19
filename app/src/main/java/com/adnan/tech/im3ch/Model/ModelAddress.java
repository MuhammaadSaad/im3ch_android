package com.adnan.tech.im3ch.Model;

public class ModelAddress {
    String address,lat,longitude;

    public ModelAddress(String address, String lat,String longitude) {
        this.address = address;
        this.lat = lat;
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat_long() {
        return lat+longitude;
    }
    public String getLat() {
        return lat;
    }
    public String getlong() {
        return longitude;
    }

    public void setLat_long(String lat,String longitude) {
        this.lat = lat;
        this.longitude = longitude;
    }
}
