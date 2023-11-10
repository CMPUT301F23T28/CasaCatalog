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

/**
 * Manages the operations related to Tag storage and retrieval in Firestore.
 */
public class TagDatabase {
    private FirebaseFirestore db;
    private CollectionReference tagsRef;
    private ArrayList<Tag> tagList;

    /**
     * Constructs a TagDatabase and initializes the connection to Firestore's tag collection,
     * setting up real-time data synchronization.
     */
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

    /**
     * Retrieves a copy of the local list of tags.
     *
     * @return An ArrayList of Tag objects currently in the local list.
     */
    public ArrayList<Tag> getTags(){
        return this.tagList;
    }

    /**
     * Finds a tag by its name.
     *
     * @param name The name of the tag to find.
     * @return The Tag object if found, null otherwise.
     */
    public Tag findTagByName(String name){
        for(Tag t : Database.tags.getTags()){
            if(t.getName().contentEquals(name)) return t;
        }
        return null;
    }

    /**
     * Creates a new tag with the given name and adds it to Firestore and the local list.
     *
     * @param name The name of the new tag to create.
     * @return The new Tag object that was created.
     */
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

    /**
     * Deletes a tag from Firestore and the local list based on the name.
     *
     * @param name The name of the tag to be deleted.
     */
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