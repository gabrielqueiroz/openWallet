package com.gqueiroz.database;

public class Item {
    private int id;
    private String name;
    private String image;
    private String color;
    private double value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Item(){

    }

    public Item(int id, String name, double value, String image, String color) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.value = value;
        this.image = image;
        this.color = color;
    }

    public Item(String name, double value, String image, String color) {
        this.name = name;
        this.image = image;
        this.value = value;
        this.image = image;
        this.color = color;
    }
}
