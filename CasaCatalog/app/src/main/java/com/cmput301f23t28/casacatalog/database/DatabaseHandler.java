package com.cmput301f23t28.casacatalog.database;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
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

    public void addItem(Item item) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        item.setId(uuidAsString);

        Log.e("FireStore", "db write failure");
        HashMap<String, Object> data = new HashMap<>();
        data.put("Id", item.getId());
        itemsRef
                .document(uuidAsString)
                .set(data)
.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Firestore", "db write success");
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FireStore", "db write failure");
                    }
                });
    }

}
