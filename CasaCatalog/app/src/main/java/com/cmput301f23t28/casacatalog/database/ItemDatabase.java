package com.cmput301f23t28.casacatalog.database;

import android.util.Log;
import android.widget.TextView;

import com.cmput301f23t28.casacatalog.helpers.Filter;
import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.helpers.ItemSorting;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Manages the operations related to Item storage and retrieval in Firestore.
 */
public class ItemDatabase {
    private FirebaseFirestore db;
    private CollectionReference itemRef;
    private ArrayList<Item> itemList;
    private ItemListAdapter adapter;

    public static final String NAME_KEY = "name";
    public static final String PRICE_KEY = "price";
    public static final String DATE_KEY = "date";
    public static final String MAKE_KEY = "make";
    public static final String MODEL_KEY = "model";
    public static final String DESCRIPTION_KEY = "description";
    public static final String COMMENT_KEY = "comments";
    public static final String SERIAL_KEY = "serialNumber";
    public static final String TAGS_KEY = "tags";

    /**
     * Constructs a ItemDatabase and initializes the connection to Firestore's itemList collection, setting up real-time data synchronization.
     */
    public ItemDatabase(){
        this("items");
    }

    /**
     * Constructs a ItemDatabase and initializes the connection to Firestore's itemList collection, setting up real-time data synchronization.
     * Directly using this constructor is not recommended, it allows setting a custom collection for the purposes of unit tests
     * @param collectionName Collection name of database
     */
    public ItemDatabase(String collectionName) {
        this.db = FirebaseFirestore.getInstance();
        this.itemRef = db.collection(collectionName);
        this.itemList = new ArrayList<>();
    }

    /**
     * Connects the item database to the RecyclerView UI
     *
     * @param adapter The ItemListAdapter currently associated with the ItemList view
     */
    public void registerListener(ItemListAdapter adapter, TextView totalValueText) {
        this.adapter = adapter;

        // Read item from database into itemList
        this.itemRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.e("Firestore", error.toString());
                return;
            }
            if (value != null) {
                this.itemList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    String itemID = doc.getId();
                    Log.d("ITEM ID QUERY", itemID);
                    String itemName = doc.getString(NAME_KEY);
                    Double itemPrice = doc.getDouble(PRICE_KEY);

                    // Convert Date object from FireStore into LocalDateTime object
                    LocalDate itemDate = LocalDate.now();   // fall back to current date if null
                    if(doc.getDate(DATE_KEY) != null) {
                        itemDate = doc.getDate(DATE_KEY).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }

                    String itemMake = doc.getString(MAKE_KEY);
                    String itemModel = doc.getString(MODEL_KEY);
                    String itemDescription = doc.getString(DESCRIPTION_KEY);
                    String itemComment = doc.getString(COMMENT_KEY);
                    String itemSerialNumber = doc.getString(SERIAL_KEY);
                    ArrayList<String> itemTagStrings = (ArrayList<String>) doc.get(TAGS_KEY);
                    ArrayList<Tag> itemTags = new ArrayList<>();
                    if (itemTagStrings != null && itemTagStrings.size() > 0) {
                        for (String t : itemTagStrings) {
                            itemTags.add(new Tag(t));
                        }
                    }

                    Log.i("Firestore", String.format("Item(%s,%s) fetched", itemName, itemPrice));
                    Item addItem = new Item();
                    addItem.setId(itemID);
                    addItem.setName(itemName);
                    addItem.setPrice(itemPrice);
                    addItem.setTags(itemTags);
                    addItem.setDate(itemDate);
                    addItem.setMake(itemMake);
                    addItem.setModel(itemModel);
                    addItem.setDescription(itemDescription);
                    addItem.setComment(itemComment);
                    if (itemComment != null) {
                        Log.d("ITEM_COMMENT_MAIN", itemComment);
                    }

                    addItem.setSerialNumber(itemSerialNumber);

                    this.itemList.add(addItem);
                }

                // Update UI to reflect changes in state
                String totalValueFormatted = String.format(Locale.ENGLISH, "$%.2f", Database.items.getTotalValue());
                totalValueText.setText(totalValueFormatted);

                // Sort item list by default settings
                // (this also updates adapter)
                this.sort(new ItemSorting());
            }
        });
    }

    /**
     * Serializes an Item into a Firebase data object
     * @param item The Item object
     * @return Firebase compatible HashMap
     */
    private HashMap<String, Object> toData(Item item){
        HashMap<String, Object> data = new HashMap<>();
        data.put(NAME_KEY, item.getName());
        data.put(PRICE_KEY, item.getPrice());

        // Firebase requires Date objects, so this converts LocalDate to Date
        data.put(DATE_KEY, Date.from(item.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        data.put(MAKE_KEY, item.getMake());
        data.put(MODEL_KEY, item.getModel());
        data.put(DESCRIPTION_KEY, item.getDescription());
        data.put(COMMENT_KEY, item.getComment());
        data.put(SERIAL_KEY, item.getSerialNumber());
        data.put(TAGS_KEY, item.getTagsAsStrings());
        return data;
    }

    /**
     * Updates an item in the Firestore database to match a given Item.
     * @param id The identifier of the Item to update.
     * @param item The Item object to be retrieve new values from.
     */
    public void update(String id, Item item){
        HashMap<String, Object> data = toData(item);
        this.itemRef.document(id).set(data)
                .addOnSuccessListener(doc -> {
                    Log.i("Firestore", "Item updated with id: " + id);
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to update item in database"));
    }

    /**
     * Adds an item to the Firestore database.
     * @param item The Item object to be added.
     */
    public void add(Item item){
        HashMap<String, Object> data = toData(item);
        this.itemRef
                .add(data)
                .addOnSuccessListener(documentReference -> {
                    String generatedDocumentId = documentReference.getId();
                    Log.i("Firestore", "Item added with ID: " + generatedDocumentId);

                    item.setId(generatedDocumentId);
                    data.put("Id", item.getId());
                    documentReference.set(data)
                            .addOnSuccessListener(aVoid -> {
                                Log.i("Firestore", "ID field updated in Firestore document");
                                Log.i("Test fb", "Set ID to " + item.getId());
                            })
                            .addOnFailureListener(e -> Log.e("Firestore", "Failed to update ID field in Firestore document: " + e.getMessage()));
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to add item to the database"));
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            itemRef
                                    .document(itemId)
                                    .delete()
                                    .addOnSuccessListener(e -> Log.i("Firestore", "Item deleted from the database"))
                                    .addOnFailureListener(e -> Log.e("Firestore", "Failed to delete item from the database"));
                        } else {
                            Log.w("Firestore", "Item does not exist in the database" + itemId);
                        }
                    } else {
                        Log.e("Firestore", "Error checking item existence: " + task.getException());
                    }
                });
    }

    /**
     * Calculates the current total value of the item list
     *
     * @return Sum of value for all items in itemList
     */
    public double getTotalValue() {
        double totalValue = 0;
        for (Item item : this.itemList) {
            if (item.getPrice() != null) {
                totalValue += item.getPrice();
            }
        }
        return totalValue;
    }

    /**
     * Retrieves a reference to the item list
     *
     * @return the itemList arraylist
     */
    public ArrayList<Item> getItems() {
        return this.itemList;
    }

    /**
     * Retrieves a reference to the Firestore collection representing the item list
     *
     * @return a CollectionReference to Firestore's itemList collection
     */
    public CollectionReference getCollection() {
        return this.itemRef;
    }

    /**
     * Given an ItemSorting, sorts the item list based on it
     * @param sorting An instance of ItemSorting
     */
    public void sort(ItemSorting sorting){
        this.itemList.sort(sorting.getComparator());

        this.adapter.notifyItemRangeChanged(0, this.adapter.getItemCount());
    }
    public void filter(Filter filter, String val1, String val2){
        //TODO: add guards to repull the database before filtering
        List<Item> filteritemlist =
                this.itemList.stream().filter(filter.getFilterPredicate(val1,val2)).collect(Collectors.toList());
        this.itemList.clear();
        this.itemList.addAll(filteritemlist);
        this.adapter.notifyItemRangeChanged(0, this.adapter.getItemCount());
    }

    /**
     * Delete all items current selected in the itemList.
     * @deprecated
     */
    public void deleteSelected() {
        ArrayList<String> selectedItemsIds = new ArrayList<String>();
        for (Item item : itemList) {
            if (item.getSelected()) selectedItemsIds.add(item.getId());
        }
        for (String id : selectedItemsIds) this.delete(id);
    }
}
