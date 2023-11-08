package com.cmput301f23t28.casacatalog.models;

import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.util.Date;

public class Item {

    private String id;
    private String name;
    private LocalDate date;
    private Double price;
    private ByteBuffer photo;
    private String tags;
    private String make;
    private String model;
    private String Description;
    private String comment;
    private Boolean selected;
    public Item(){

    }
    public  String getId() {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ByteBuffer getPhoto() {
        return photo;
    }

    public void setPhoto(ByteBuffer photo) {
        this.photo = photo;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
