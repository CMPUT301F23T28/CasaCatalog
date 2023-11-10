package com.cmput301f23t28.casacatalog.database;
import android.content.Context;

import com.cmput301f23t28.casacatalog.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class UserDatabase {
    private FirebaseFirestore db;
    private CollectionReference collection;

    public UserDatabase(){
        this.db = FirebaseFirestore.getInstance();
        this.collection = db.collection("users");
    }

    public CollectionReference getCollection(){ return this.collection; }

    public User createUser(Context context, String name) {
        User newUser = new User(context, name);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("deviceId", newUser.getDeviceId());

        // TODO: log success and failure
        collection.document(newUser.getDeviceId()).set(data);

        return newUser;
    }
}
