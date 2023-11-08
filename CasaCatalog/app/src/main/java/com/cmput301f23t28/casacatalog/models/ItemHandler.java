package com.cmput301f23t28.casacatalog.models;

import android.util.Log;

import com.cmput301f23t28.casacatalog.database.DatabaseHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.CollectionReference;


import java.util.ArrayList;

public class ItemHandler
{
    private DatabaseHandler db;
    private ArrayList<Item> itemList;
    private ArrayList<String> sorts;

    private ArrayList<String> filters;

    public ItemHandler() {
        this.db = new DatabaseHandler();
        itemList = new ArrayList<Item>();
    }

    public void addItem(Item item){
        db.addItemDatabase(item);
    }

    public void setItem(Item item, int i){
        itemList.set(i, item);
    }

    public void deleteItem(Item item){
        db.deleteItemDatabase(item.getId());
    }

    public void deleteItem(int i){
//        itemList.remove(itemList.get(i).getId());
    }

    public void deleteSelectedItems(){
        ArrayList<String> selectedItemsIds = new ArrayList<String>();
        for (int i=0; i < itemList.size(); i++){
            if (itemList.get(i).getSelected()){
                selectedItemsIds.add(itemList.get(i).getId());
            }
        }
        for (int i=0; i < selectedItemsIds.size(); i++){
            db.deleteItemDatabase(selectedItemsIds.get(i));
        }
    }

    public Item getItem(int i){
        Item item = itemList.get(i);
        return item;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }


    public DatabaseHandler getDb() {
        return db;
    }
}
