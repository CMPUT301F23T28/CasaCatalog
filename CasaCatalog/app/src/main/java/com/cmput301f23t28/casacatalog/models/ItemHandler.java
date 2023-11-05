package com.cmput301f23t28.casacatalog.models;

import com.cmput301f23t28.casacatalog.database.DatabaseHandler;

import java.util.ArrayList;

public class ItemHandler
{
    DatabaseHandler db;


    private ArrayList<Item> itemList;
    private ArrayList<String> sorts;

    private ArrayList<String> filters;

    public ItemHandler() {
        this.db = new DatabaseHandler();
        itemList = new ArrayList<Item>();
    }

    public void addItem(Item item){
        itemList.add(item);
        db.addItemDatabase(item);
    }

    public void setItem(Item item, int i){
        itemList.set(i, item);
    }

    public void deleteItem(Item item){

        itemList.remove(item);
        db.deleteItemDatabase(item.getId());
    }

    public void deleteItem(int i){
        itemList.remove(i);
    }

    public void deleteSelectedItems(){
        ArrayList<Item> selectedItems = new ArrayList<Item>();
        for (int i=0; i < itemList.size(); i++){
            if (itemList.get(i).getSelected()){
                selectedItems.add(itemList.get(i));
            }
        }
        itemList.removeAll(selectedItems);
    }

    public Item getItem(int i){
        Item item = itemList.get(i);
        return item;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }
}
