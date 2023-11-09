package com.cmput301f23t28.casacatalog.models;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Item implements Serializable {

    private String id;
    private String name;
    private Double price;
    private Date date;
    private ByteBuffer photo;
    private ArrayList<Tag> tags;
    private String make;
    private String model;
    private String Description;
    private String comment;
    private String serialNumber;
    private Boolean selected;
    public Item(){
        this.tags = new ArrayList<>();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public ArrayList<Tag> getTags() {
        return tags;
    }

    // This method is just so it can be stored in database
    // probably a better way with DocumentReference
    public List<String> getTagsAsStrings(){
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if( !this.tags.contains(tag) ) this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        if( this.tags.contains(tag) ) this.tags.remove(tag);
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
