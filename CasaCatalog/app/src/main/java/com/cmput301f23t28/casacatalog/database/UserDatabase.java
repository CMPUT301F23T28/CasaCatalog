package com.cmput301f23t28.casacatalog.database;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.cmput301f23t28.casacatalog.models.User;
import com.cmput301f23t28.casacatalog.views.MainActivity;
import com.cmput301f23t28.casacatalog.views.NewUserActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

/**
 * Manages the operations related to User storage and retrieval in Firestore.
 */
public class UserDatabase {
    private FirebaseFirestore db;
    private CollectionReference collection;
    private User currentUser;

    /**
     * Constructs a UserDatabase and initializes the connection to Firestore's users collection,
     * setting up real-time data synchronization.
     */
    public UserDatabase(){
        this.db = FirebaseFirestore.getInstance();
        this.collection = db.collection("users");

        // Attempt to load current user from database.
        collection.document(MainActivity.deviceId).get().addOnCompleteListener(task -> {
            if( task.isSuccessful() && task.getResult().exists() ){
                this.currentUser = new User(MainActivity.deviceId, task.getResult().getString("name"));
            }
        });
    }

    /**
     * Retrieves a reference to the users collection in Firestore
     * @return A CollectionReference to users in Firestore
     */
    public CollectionReference getCollection(){ return this.collection; }

    /**
     * Creates a new User and stores in database
     * @param context Any context in the application
     * @param name The name of the new user
     */
    public void createUser(String deviceId, String name) {
        User newUser = new User(deviceId, name);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", newUser.getName());
        data.put("deviceId", newUser.getDeviceId());

        // TODO: log success and failure
        collection.document(newUser.getDeviceId()).set(data);
        this.currentUser = newUser;
    }

    /**
     * Retrieves a reference to the User object representing the current client
     *
     * @return A User object representing the current client
     */
    public User getCurrentUser(Context context){
        return this.currentUser;
    }
}
