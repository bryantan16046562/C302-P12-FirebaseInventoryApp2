package com.example.a16046562.firebaseinventoryapp2;

import java.io.Serializable;
public class Item implements Serializable {
    private String id;
    private String name;
    private double cost;

    public Item() {
    }
    public Item(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name ;
    }
}
