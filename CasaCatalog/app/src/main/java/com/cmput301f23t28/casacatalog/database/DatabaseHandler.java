package com.cmput301f23t28.casacatalog.database;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.UUID;

public class DatabaseHandler {
    private FirebaseFirestore db;
    private CollectionReference itemsRef;

    public DatabaseHandler(){
        db = FirebaseFirestore.getInstance();
        itemsRef = db.collection("itemList");
    }

    public void addItemDatabase(Item item) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        item.setId(uuidAsString);

        Log.e("FireStore", "db write failure");
        HashMap<String, Object> data = new HashMap<>();
        data.put("Id", item.getId());
        data.put("name", item.getName());
//        data.put("date", item.getDate());
        data.put("price", item.getPrice());
//        data.put("make", item.getMake());
//        data.put("model", item.getModel());
//        data.put("description", item.getDescription());
//        data.put("comments", item.getComment());
//        data.put("tags", item.getTags());

        itemsRef
                .add(data) // Use .add(data) to add a new document and get the auto-generated ID
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String generatedDocumentId = documentReference.getId();
                        Log.i("Firestore", "Item added with ID: " + generatedDocumentId);
                        // Set the generated ID in your Item object
                        item.setId(generatedDocumentId);
                        // You can perform further operations with the ID or the item as needed.
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Failed to add item to the database");
                    }
                });
    }

    public void deleteItemDatabase(String itemId) {
        // Check if the item exists before attempting to delete it
        itemsRef
                .document(itemId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // The item exists; you can delete it
                                itemsRef
                                        .document(itemId)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Log.i("Firestore", "Item deleted from the database");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.e("Firestore", "Failed to delete item from the database");
                                            }
                                        });
                            } else {
                                Log.w("Firestore", "Item does not exist in the database");
                            }
                        } else {
                            Log.e("Firestore", "Error checking item existence: " + task.getException());
                        }
                    }
                });
    }

}
