package com.example.shereen.dipolmafinalproject;

/**
 * Created by Shereen on 10/24/2018.
 */

public class Product {
    String name,contact_name;
    double lat,lng;
    int days,price,avail,id;
    public byte[] image;

    public Product(String name,double lat,double lng, int price, int days, int avail, String contact_name, int id, byte[] image) {
        this.name = name;
        this.lat=lat;
        this.lng=lng;
        this.price = price;
        this.days = days;
        this.avail = avail;
        this.contact_name = contact_name;
        this.id=id;
        this.image=image;

    }
}
