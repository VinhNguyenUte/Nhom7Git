package com.ktck124.lop124LTDD04.nhom07.model;

import org.w3c.dom.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private String brand;
    private String name;
    private String description;
    private double price;
    private long carId;
    private List<Comment> comments = new ArrayList<>();

    private LocalDate createdAt;

    public Car(String brand, String name, String description, double price, long carId, List<Comment> comments, LocalDate time) {
        this.brand = brand;
        this.name = name;
        this.description = description;
        this.price = price;
        this.carId = carId;
        this.comments = comments;
        this.createdAt = time;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}