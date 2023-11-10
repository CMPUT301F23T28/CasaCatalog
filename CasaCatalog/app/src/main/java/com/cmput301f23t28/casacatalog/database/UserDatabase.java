package com.cmput301f23t28.casacatalog.database;
import android.content.Context;
import android.util.Log;

import com.cmput301f23t28.casacatalog.models.Tag;
import com.cmput301f23t28.casacatalog.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDatabase {
    private FirebaseFirestore db;
    private CollectionReference usersRef;

    public UserDatabase(){
        this.db = FirebaseFirestore.getInstance();
        this.usersRef = db.collection("users");
    }

    public User createUser(Context context, String name) {
        User newUser = new User(context, name);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("deviceId", newUser.getDeviceId());

        // TODO: log success and failure
        usersRef.document(newUser.getDeviceId()).set(data);

        return newUser;
    }

    /*
     * Given a device ID, see if it is associated to an existing user
     */
    public boolean isRegistered(String deviceId){
        boolean registered = false;
        this.usersRef.document(deviceId).get().addOnCompleteListener(task -> {
            registered = task.isSuccessful() && task.getResult().exists());
        });
        return registered;
    }
}
