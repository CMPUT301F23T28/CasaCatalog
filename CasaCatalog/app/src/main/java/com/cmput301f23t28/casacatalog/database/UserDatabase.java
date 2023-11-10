package com.cmput301f23t28.casacatalog.database;
import android.content.Context;

import com.cmput301f23t28.casacatalog.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserDatabase {
    private FirebaseFirestore db;
    private CollectionReference collection;
    private User currentUser;

    public UserDatabase(){
        this.db = FirebaseFirestore.getInstance();
        this.collection = db.collection("users");
    }

    public CollectionReference getCollection(){ return this.collection; }

    public void createUser(Context context, String name) {
        User newUser = new User(context, name);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("deviceId", newUser.getDeviceId());

        // TODO: log success and failure
        collection.document(newUser.getDeviceId()).set(data);
        this.currentUser = newUser;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }
}
