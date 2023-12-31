package com.cmput301f23t28.casacatalog.database;

import android.util.Log;

import com.cmput301f23t28.casacatalog.helpers.DateFormatter;
import com.cmput301f23t28.casacatalog.views.MainActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Manages the operations related to User storage and retrieval in Firestore.
 */
public class UserDatabase {
    private static CollectionReference collection;
    private static HashSet<String> takenNames;

    private static final String NAME_KEY = "name";
    private static final String CREATED_KEY = "created";

    private static String userName;
    private static Date created;

    /**
     * Constructs a UserDatabase and initializes the connection to Firestore's users collection,
     * setting up real-time data synchronization.
     */
    public UserDatabase(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        collection = db.collection("users");
        takenNames = new HashSet<>();

        // Read users from database, hydrate static fields with correct user info
        collection.addSnapshotListener((value, error) -> {
            if (error != null) { Log.e("Firestore", error.toString()); return; }
            if (value != null) {
                takenNames.clear();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        // Retrieve current user information
                        if(doc.getId().equals(MainActivity.deviceId)){
                            userName = doc.getString(NAME_KEY);
                            created = doc.getDate(CREATED_KEY);
                            Log.i("Firestore", "Updated current user data");
                        }

                        // Keep record of all used names
                        takenNames.add(doc.getString(NAME_KEY));
                    }
                }
            }
        });
    }

    /**
     * Retrieves a reference to the users collection in Firestore
     * @return A CollectionReference to users in Firestore
     */
    public static CollectionReference getCollection(){ return collection; }

    /**
     * Retrieves the current user's name
     * @return String user name
     */
    public static String getUserName(){
        return userName;
    }

    /**
     * Retrieves when the current user created their account
     * @return Date formatted string of user creation date
     */
    public static String getCreated(){
        return DateFormatter.getFormattedDate(created);
    }

    /**
     * Tests if a username is already taken by another user.
     * @param name The String name to test.
     * @return true if the username is unique in the database, otherwise false.
     */
    public static boolean isNameUnique(String name){
        return !takenNames.contains(name);
    }

    /**
     * Creates a new User and stores in database
     * @param name The name of the new user
     */
    public void createUser(String name) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("created", new Date());

        collection.document(MainActivity.deviceId).set(data)
                .addOnSuccessListener(doc -> Log.i("Firestore", "Created new user with name: " + name))
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to create new user"));
    }
}
