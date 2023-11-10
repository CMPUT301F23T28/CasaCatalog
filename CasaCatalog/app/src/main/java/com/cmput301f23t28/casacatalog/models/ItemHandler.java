package com.cmput301f23t28.casacatalog.models;

import com.cmput301f23t28.casacatalog.database.DatabaseHandler;
import java.util.ArrayList;

/**
 * Handles operations related to items such as adding, deleting, and retrieving items from the database.
 */
public class ItemHandler {
    private DatabaseHandler db;
    private ArrayList<Item> itemList;

    /**
     * Constructs an ItemHandler with an initialized database handler and an empty item list.
     */
    public ItemHandler() {
        this.db = new DatabaseHandler();
        itemList = new ArrayList<Item>();
    }

    /**
     * Adds an item to the database.
     *
     * @param item The item to be added to the database.
     */
    public void addItem(Item item) {
        db.addItemDatabase(item);
    }

    /**
     * Replaces the item at the specified index with a new item.
     *
     * @param item The new item to set in the list.
     * @param i    The index of the item to replace.
     */
    public void setItem(Item item, int i) {
        itemList.set(i, item);
    }


    public void deleteItem(Item item) {
        db.deleteItemDatabase(item.getId());
    }



    public void deleteItem(int i){
        db.deleteItemDatabase(itemList.get(i).getId());
    }

    public void deleteItem(String ID){
        db.deleteItemDatabase(ID);
    }
    public void deleteSelectedItems(){
        ArrayList<String> selectedItemsIds = new ArrayList<String>();
        for (Item item : itemList) {
            if (item.getSelected()) {
                selectedItemsIds.add(item.getId());
            }
        }
        for (String id : selectedItemsIds) {
            db.deleteItemDatabase(id);
        }
    }

    /**
     * Retrieves an item from the list based on the index.
     *
     * @param i The index of the item to retrieve.
     * @return The item at the specified index.
     */
    public Item getItem(int i) {
        return itemList.get(i);
    }

    /**
     * Gets the list of items.
     *
     * @return The current list of items.
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * Gets the database handler.
     *
     * @return The database handler associated with this item handler.
     */
    public DatabaseHandler getDb() {
        return db;
    }
}