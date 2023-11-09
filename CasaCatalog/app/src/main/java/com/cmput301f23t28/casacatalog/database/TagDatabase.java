package com.cmput301f23t28.casacatalog.database;
import android.util.Log;

import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class TagDatabase {
    private FirebaseFirestore db;
    private CollectionReference tagsRef;
    private ArrayList<Tag> tagList;

    public TagDatabase(){
        this.db = FirebaseFirestore.getInstance();
        this.tagsRef = db.collection("tags");
        this.tagList = new ArrayList<>();

        // Read tags from database into tagList
        this.tagsRef.addSnapshotListener((value, error) -> {
            if (error != null){
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null){
                for (QueryDocumentSnapshot doc : value){
                    if(doc != null) {
                        Tag newTag = new Tag(doc.getId());
                        if(doc.getLong("uses") != null) {
                            newTag.setUses(doc.getLong("uses").intValue());
                        }
                        this.tagList.add(newTag);
                    }
                }
            }
        });
    }

    public ArrayList<Tag> getTags(){
        return this.tagList;
    }

    public Tag findTagByName(String name){
        for(Tag t : Database.tags.getTags()){
            if(t.getName().contentEquals(name)) return t;
        }
        return null;
    }

    public Tag createTag(String name) {
        Tag newTag = new Tag(name);

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", newTag.getName());
        data.put("uses", newTag.getUses());

        // TODO: log success and failure
        tagsRef.document(newTag.getName()).set(data).addOnSuccessListener(doc -> {
            this.tagList.add(newTag);
        });
        return newTag;
    }

    public void deleteTag(String name) {
        DocumentReference doc = tagsRef.document(name);
        doc.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()){
                    doc.delete()
                            .addOnSuccessListener(unused -> {
                                // Update local taglist accordingly
                                this.tagList.removeIf(tag -> tag.getName().equals(name));

                                Log.i("Firestore", "Tag deleted from the database");
                            })
                            .addOnFailureListener(e -> Log.e("Firestore", "Failed to delete tag from the database"));
                } else {
                    Log.w("Firestore", "Tag does not exist in the database: " + name);
                }
            } else {
                Log.e("Firestore", "Error checking tag existence: " + task.getException());
            }
        });
    }

}
