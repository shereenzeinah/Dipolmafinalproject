package com.example.shereen.dipolmafinalproject;

/**
 * Created by Shereen on 10/24/2018.
 */

public class Product {
    String name,contact_name;
    double lat,lng;
    int days,price,avail,id;
    public byte[] image;

    public void setAvail(int avail) {
        this.avail = avail;
    }

    public Product(String name, double lat, double lng, int price, int days, int avail, String contact_name, int id, byte[] image) {
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

    public String getName() {
        return name;
    }

    public String getContact_name() {
        return contact_name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getDays() {
        return days;
    }

    public int getPrice() {
        return price;
    }

    public int getAvail() {
        return avail;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }
}
