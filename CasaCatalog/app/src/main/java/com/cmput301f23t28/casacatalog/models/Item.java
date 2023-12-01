package com.cmput301f23t28.casacatalog.models;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private LocalDate date;
    private ByteBuffer photo;
    private List<String> photoURL; // used for cloud storage reference
    private ArrayList<Tag> tags;
    private String make;
    private String model;
    private String description;
    private String comment;
    private String serialNumber;
    private Boolean selected = false;

    /**
     * Default constructor initializing the tags list.
     */
    public Item(){
        // Defaults
        this.tags = new ArrayList<>();
        this.date = LocalDate.now();
        this.price = 0.0;
        this.make = "";
        this.model = "";
        this.description = "";
        this.comment = "";
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
     * Returns empty string if there is none set.
     * @return A string representing the item's name.
     */
    public String getName() {
        return this.name != null ? this.name : "";
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
     * Returns current date if there is no date set.
     * @return A LocalDateTime object representing when the item was acquired.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Gets the formatted purchase or acquisition date of the item.
     * @return A String representing a formatted date of when the item was acquired.
     */
    public String getFormattedDate() {
        // TODO: probably define this format in an xml
        return this.date.format(DateTimeFormatter.ofPattern("MMM. d, yyyy"));
    }


    /**
     * Sets the purchase or acquisition date of the item.
     * @param date A LocalDateTime object containing the new acquisition date of the item.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the price of the item.
     * Returns 0 if there is no price set.
     * @return A Double representing the item's price.
     */
    public Double getPrice() {
        return this.price;
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
     * Converts the list of tags to an alphabetically sorted list of strings representing tag names.
     * This method is used for database storage and sorting.
     * @return A List of strings representing the names of the tags.
     */
    public List<String> getTagsAsStrings(){
        return tags.stream().map(Tag::getName).sorted().collect(Collectors.toList());
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
     * Returns empty string if there is none set.
     * @return A string representing the item's make.
     */
    public String getMake() {
        return this.make;
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
     * Returns empty string if there is none set.
     * @return A string representing the item's model.
     */
    public String getModel() {
        return this.model;
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
     * Returns empty string if there is none set.
     * @return A string representing the item's description.
     */
    public String getDescription() {
        return this.description;
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
     * Returns empty string if there is none set.
     * @return A string representing any comments associated with the item.
     */
    public String getComment() {
        return this.comment;
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

    /**
     * Toggles the selected state.
     */
    public void toggleSelected() {
        this.selected = !this.selected;
    }

    public List<String> getPhotoURLs() {
        return photoURL;
    }
    public void setPhotoURLs(List<String> photoURL) {
        this.photoURL = photoURL;
    }
}
