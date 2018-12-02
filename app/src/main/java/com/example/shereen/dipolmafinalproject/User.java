package com.example.shereen.dipolmafinalproject;

/**
 * Created by Shereen on 10/24/2018.
 */

public class User {
     String name,email,password;
     double lat,lng;
     int phone;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getPhone() {
        return phone;
    }

    public User(String name, int phone, double lat, double lng, String email, String password) {
        this.name = name;
        this.email = email;
        this.lat=lat;
        this.lng=lng;
        this.password = password;
        this.phone = phone;

    }}
