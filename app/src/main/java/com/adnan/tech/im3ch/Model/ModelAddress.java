package com.adnan.tech.im3ch.Model;

public class ModelAddress {
    String address,lat_long;

    public ModelAddress(String address, String lat_long) {
        this.address = address;
        this.lat_long = lat_long;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat_long() {
        return lat_long;
    }

    public void setLat_long(String lat_long) {
        this.lat_long = lat_long;
    }
}
