package com.cmput301f23t28.casacatalog.database;
import android.util.Log;

import androidx.annotation.NonNull;

import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Manages the operations related to Item storage and retrieval in Firestore.
 */
public class ItemDatabase {
    private FirebaseFirestore db;
    private CollectionReference itemRef;
    private ArrayList<Item> itemList;

    /**
     * Constructs a ItemDatabase and initializes the connection to Firestore's tag collection,
     * setting up real-time data synchronization.
     */
    public ItemDatabase() {
        this.db = FirebaseFirestore.getInstance();
        this.itemRef = db.collection("itemList");
        this.itemList = new ArrayList<>();
    }

    public void registerListener(ItemListAdapter adapter){
        // Read item from database into itemList
        this.itemRef.addSnapshotListener((value, error) -> {
            if (error != null){
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null){
                this.itemList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    String itemID = doc.getId();
                    Log.d("ITEM ID QUERY", itemID);
                    String itemname = doc.getString("name");
                    Double pricename = doc.getDouble("price");
                    String dateinstring = doc.getString("date");

                    String itemMake = doc.getString("make");
                    String itemModel = doc.getString("model");
                    String itemDescription = doc.getString("description");
                    String itemComment = doc.getString("comments");
                    String itemSerialNumber = doc.getString("serialNumber");
                    ArrayList<String> itemTagStrings = (ArrayList<String>) doc.get("tags");
                    ArrayList<Tag> itemTags = new ArrayList<>();
                    if (itemTagStrings != null && itemTagStrings.size() > 0) {
                        for (String t : itemTagStrings) {
                            itemTags.add(new Tag(t));
                        }
                    }

                    Log.i("Firestore", String.format("Item(%s,%s) fetched", itemname,
                            pricename));
                    Item addItem = new Item();
                    addItem.setId(itemID);
                    addItem.setName(itemname);
                    addItem.setPrice(pricename);
                    addItem.setTags(itemTags);

                    //add date to the addItem
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                    if (dateinstring != null) {
                        try {
                            Date date = sdf.parse(dateinstring);
                            addItem.setDate(date);
                        } catch (ParseException e) {
                            Log.e("ParseException", "ParseException" + e.toString());
                        }
                    }
                    // (Max) SSH IM NOT CHANGING THE DATE TO A STRING
                    addItem.setDateFormatted(dateinstring);

                    addItem.setMake(itemMake);
                    addItem.setModel(itemModel);
                    addItem.setDescription(itemDescription);
                    addItem.setComment(itemComment);
                    if (itemComment != null) {
                        Log.d("ITEM_COMMENT_MAIN", itemComment);
                    }

                    addItem.setSerialNumber(itemSerialNumber);

                    this.itemList.add(addItem);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Adds an item to the Firestore database.
     *
     * @param item The Item object to be added.
     */
    public void add(Item item) {

        HashMap<String, Object> data = new HashMap<>();
        data.put("name", item.getName());
        data.put("price", item.getPrice());
        if (item.getDate() != null){
            // SSSHHH I DIDNT CHANGE ANYTHING
            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            //data.put("date", sdf.format(item.getDate()));
            data.put("date", item.getDateFormatted());
        }

        data.put("make", item.getMake());
        data.put("model", item.getModel());
        data.put("description", item.getDescription());
        data.put("comments", item.getComment());
        data.put("serialNumber", item.getSerialNumber());
        data.put("tags", item.getTagsAsStrings());

        this.itemRef
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String generatedDocumentId = documentReference.getId();
                        Log.i("Firestore", "Item added with ID: " + generatedDocumentId);

                        item.setId(generatedDocumentId);
                        data.put("Id", item.getId());
                        documentReference.set(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("Firestore", "ID field updated in Firestore document");
                                        Log.i("Test fb", "Set ID to " + item.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("Firestore", "Failed to update ID field in Firestore document: " + e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Failed to add item to the database");
                    }
                });
    }

    /**
     * Deletes an item from the Firestore database.
     *
     * @param itemId The ID of the item to be deleted.
     */
    public void delete(String itemId) {
        // Check if the item exists before attempting to delete it
        this.itemRef
                .document(itemId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                itemRef
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
                                Log.w("Firestore", "Item does not exist in the database" + itemId);
                            }
                        } else {
                            Log.e("Firestore", "Error checking item existence: " + task.getException());
                        }
                    }
                });
    }

    public double getTotalValue(){
        double totalValue = 0;
        for (Item item : this.itemList) {
            if (item.getPrice() != null) {
                totalValue += item.getPrice();
            }
        }
        return totalValue;
    }

    public ArrayList<Item> getItems(){
        return this.itemList;
    }

    public CollectionReference getCollection(){
        return this.itemRef;
    }

    /**
     * Delete all items current selected in the itemList.
     * @deprecated
     */
    public void deleteSelected(){
        ArrayList<String> selectedItemsIds = new ArrayList<String>();
        for (Item item : itemList) {
            if (item.getSelected()) {
                selectedItemsIds.add(item.getId());
            }
        }
        for (String id : selectedItemsIds) {
            this.delete(id);
        }
    }
}
