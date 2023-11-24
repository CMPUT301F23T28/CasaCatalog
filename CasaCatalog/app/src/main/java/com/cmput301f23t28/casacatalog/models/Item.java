package com.cmput301f23t28.casacatalog.models;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents an item with various properties such as name, price, tags, and more.
 * This class implements Serializable, allowing its instances to be serialized for storage or transmission.
 */
public class Item implements Serializable {

    private String id;
    private String name;
    private Double price;
    private Date date;
    private String dateFormatted; // I don't want to figure out how to do this another way right now
    private ByteBuffer photo;
    private ArrayList<Tag> tags;
    private String make;
    private String model;
    private String description;
    private String comment;
    private String serialNumber;
    private Boolean selected;

    /**
     * Default constructor initializing the tags list.
     */
    public Item(){
        this.tags = new ArrayList<>();
    }

    /**
     * Gets the unique identifier of the item.
     * @return A string representing the item's ID.
     */
    public  String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the item.
     * @param id A string containing the new ID of the item.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the name of the item.
     * @return A string representing the item's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     * @param name A string containing the new name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the purchase or acquisition date of the item.
     * @return A Date object representing when the item was acquired.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the formatted purchase or acquisition date of the item.
     * @return A String representing a formatted date of when the item was acquired.
     */
    public String getDateFormatted() { // (Max) Im doing this for the editItem i cannot be bothered
        return dateFormatted;
    }

    /**
     * Sets the formatted purchase or acquisition date of the item.
     * @param dateFormatted A String representing a formatted date of when the item was acquired.
     */
    public void setDateFormatted(String dateFormatted) {
        this.dateFormatted = dateFormatted;
    }

    /**
     * Sets the purchase or acquisition date of the item.
     * @param date A Date object containing the new acquisition date of the item.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the price of the item.
     * @return A Double representing the item's price.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the price of the item.
     * @param price A Double containing the new price of the item.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets the photo of the item as a ByteBuffer.
     * @return A ByteBuffer representing the item's photo.
     */
    public ByteBuffer getPhoto() {
        return photo;
    }

    /**
     * Sets the photo of the item.
     * @param photo A ByteBuffer containing the new photo of the item.
     */
    public void setPhoto(ByteBuffer photo) {
        this.photo = photo;
    }

    /**
     * Gets the list of tags associated with the item.
     * @return An ArrayList of Tag objects associated with the item.
     */
    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * Converts the list of tags to a list of strings representing tag names.
     * This method is used for database storage.
     * @return A List of strings representing the names of the tags.
     */
    public List<String> getTagsAsStrings(){
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    /**
     * Sets the list of tags associated with the item.
     * @param tags An ArrayList of Tag objects to associate with the item.
     */
    public void setTags(ArrayList<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Adds a tag to the item, if it is not already present.
     * @param tag A Tag object to add to the item.
     */
    public void addTag(Tag tag) {
        if( !this.tags.contains(tag) ) this.tags.add(tag);
    }

    /**
     * Removes a tag from the item, if it exists.
     * @param tag A Tag object to remove from the item.
     */
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    /**
     * Gets the make of the item.
     * @return A string representing the item's make.
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make of the item.
     * @param make A string containing the new make of the item.
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Gets the model of the item.
     * @return A string representing the item's model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the item.
     * @param model A string containing the new model of the item.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the description of the item.
     * @return A string representing the item's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     * @param description A string containing the new description of the item.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the comment associated with the item.
     * @return A string representing any comments associated with the item.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment associated with the item.
     * @param comment A string containing the new comment for the item.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the serial number of the item.
     * @return A string representing the item's serial number.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the serial number of the item.
     * @param serialNumber A string containing the new serial number of the item.
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    /**
     * Gets the selection status of the item.
     * @return A Boolean representing whether the item is selected or not.
     */
    public Boolean getSelected() {
        return selected;
    }

    /**
     * Sets the selection status of the item.
     * @param selected A Boolean containing the new selection status of the item.
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
