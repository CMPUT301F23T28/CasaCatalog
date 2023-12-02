package com.cmput301f23t28.casacatalog.database;

import android.util.Log;

import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Manages the operations related to Tag storage and retrieval in Firestore.
 */
public class TagDatabase {
    private FirebaseFirestore db;
    private CollectionReference tagsRef;
    private ArrayList<Tag> tagList;

    /**
     * Constructs a TagDatabase and initializes the connection to Firestore's tag collection, setting up real-time data synchronization.
     */
    public TagDatabase(){
        this("tags");
    }

    /**
     * Constructs a TagDatabase and initializes the connection to Firestore's tag collection, setting up real-time data synchronization.
     * Directly using this constructor is not recommended, it allows setting a custom collection for the purposes of unit tests
     * @param collectionName Collection name of database
    **/
    public TagDatabase(String collectionName) {
        this.db = FirebaseFirestore.getInstance();
        this.tagsRef = db.collection(collectionName);
        this.tagList = new ArrayList<>();

        // Read tags from database into tagList
        this.tagsRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null) {
                // Clear local taglist before reading from database
                this.tagList.clear();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc != null) {
                        Tag newTag = new Tag(doc.getId());
                        if (doc.getLong("uses") != null) {
                            newTag.setUses(doc.getLong("uses").intValue());
                        }
                        Log.i("Firestore", String.format("Tag(%s,%s) fetched", newTag.getName(), newTag.getUses()));

                        // Update local taglist accordingly
                        this.tagList.add(newTag);
                    }
                }
            }
        });
    }

    /**
     * Retrieves a copy of the local list of tags.
     * @return An ArrayList of Tag objects currently in the local list.
     */
    public ArrayList<Tag> getTags() {
        return this.tagList;
    }

    /**
     * Retrieves a copy of the local list of tags, sorted.
     * @return A sorted ArrayList of Tag objects currently in the local list.
     */
    public ArrayList<Tag> getSortedTags() {
        ArrayList<Tag> tags = this.getTags();
        Collections.sort(tags);
        return tags;
    }

    /**
     * Finds a tag by its name.
     *
     * @param name The name of the tag to find.
     * @return The Tag object if found, null otherwise.
     */
    public Tag findTagByName(String name) {
        for (Tag t : getTags()) {
            if (t.getName().contentEquals(name)) return t;
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
        return this.updateTag(name, new Tag(name));
    }

    /**
     * Updates an existing tag in the local list and Firestore database.
     *
     * @param name The name of the tag to update
     * @param tag A tag to get the new properties from
     */
    public Tag updateTag(String name, Tag tag) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", tag.getName());
        data.put("uses", tag.getUses());

        tagsRef.document(name).set(data)
                .addOnSuccessListener(doc -> {
                    Log.i("Firestore", "Tag created or updated with name: " + tag.getName());
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to modify tag in database"));
        return tag;
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
                if (document.exists()) {
                    doc.delete()
                            .addOnSuccessListener(unused -> {
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
