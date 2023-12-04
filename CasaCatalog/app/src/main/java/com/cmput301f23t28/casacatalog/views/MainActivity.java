package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.Camera.TextRecognitionHelper;
import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Filter;
import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.helpers.VisibilityCallback;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The main activity that starts when the application is launched.
 * It initializes the database, item handler, and sets up the main user interface,
 * including the RecyclerView for items and listeners for UI elements.
 */
public class MainActivity extends AppCompatActivity implements VisibilityCallback {
    public static String deviceId;
    private RecyclerView itemListView;
    private ItemListAdapter itemAdapter;
    private FloatingActionButton editTagsButton;
    private FloatingActionButton trashButton;
//    boolean once = true;
    ArrayList<Filter> filters;

    private ArrayList<Item> items;

    /**
     * Initializes the activity, sets up the database, and configures the RecyclerView.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filters = new ArrayList<>();

        TextRecognitionHelper textHelper = new TextRecognitionHelper(this);
        textHelper.recognizeTextFromImage();

        // Record device identifier
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Initialize all databases
        Database.initialize();
        items = Database.items.getItems();

        // If first time using app, start create user activity
        Database.users.getCollection().document(deviceId).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful() || !task.getResult().exists()) {
                startActivity(new Intent(this, NewUserActivity.class));
            }
        });

        trashButton = findViewById(R.id.delete_items_button);
        editTagsButton = findViewById(R.id.add_tag_items_button);

        // Item list logic
        ActivityResultLauncher<Intent> editItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Item item = result.getData().getParcelableExtra("item");
                        Database.items.update(item.getId(), item);
                    }
                }
        );

        itemAdapter = new ItemListAdapter(this, items, editItemLauncher, this);
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);
        itemListView.setLayoutManager(new LinearLayoutManager(this));
        itemListView.setItemAnimator(null);     // fixes bug in Android
        Database.items.registerListener(itemAdapter, findViewById(R.id.InventoryValueNumber),
                filters);

        // Sends the user to the 'add item' activity, allowing them to input their item and all of its relevant details.
        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddItemActivity.class)));

        ActivityResultLauncher<Intent> editFilterLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            ArrayList<Filter> list = result.getData().getParcelableArrayListExtra("filters");
                            filters.clear();
                            // Use the list as needed
                            for (int i =0; i < list.size(); i++){
                                Log.d("Filter "+ Integer.toString(i+1),
                                        list.get(i).getVal1() +" " + list.get(i).getVal2() + " " + list.get(i).getCurrentFilterType() + " " + list.get(i).getCurrentType());
                                filters.add(list.get(i));
                            }
                        }
                    }
                }
        );

        findViewById(R.id.FilterButton).setOnClickListener(view -> {
            Intent i = new Intent(this, FilterActivity.class);
            i.putExtra("filters", filters);
            editFilterLauncher.launch(i);
        });

        ArrayList<Item> selectedItems = new ArrayList<>();
        ActivityResultLauncher<Intent> editTagsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            ArrayList<Tag> newTags = result.getData().getParcelableArrayListExtra("tags");
                            // loop through all selected items
                            for (Item selectedItem : selectedItems) {
                                // loop through all items and if they match the selected items then set their tags
                                for (Item item : items) {
                                    if (item == selectedItem) {
                                        item.setTags(newTags);
                                        Database.items.update(item.getId(), item);
                                    }
                                }
                            }
                            itemAdapter.notifyDataSetChanged();

                        }
                    }
                }
        );

        editTagsButton.setOnClickListener(v -> {
            // Receives result from EditTagsActivity
            Set<Tag> tags = new LinkedHashSet<>();
            boolean anySelected = false;
            selectedItems.clear();
            for (Item item: items) {

                if (item.getSelected()) {
                    anySelected = true;
                    selectedItems.add(item);
                    for (Tag tag : item.getTags()) {
                        tags.add(tag);
                    }
                }
            }

            if (anySelected && selectedItems.size() > 0) {
                itemAdapter.setEditingState(false);
                Intent i = new Intent(this, EditTagsActivity.class);
                i.putExtra("tags", new ArrayList(tags));
                editTagsLauncher.launch(i);
            }

            if (!anySelected) {
                String text = "No items were selected.";
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }

            // clear selection status
            for (Item item : items) {
                item.setSelected(false);
            }

            // hide buttons
            toggleVisibility();
        });

        // handles deletion of selection items
        trashButton.setOnClickListener(v -> {
            boolean anySelected = false;
            for (Item item: items) {

                if (item.getSelected()) {
                    anySelected = true;
                    Log.i("CRUD", "Deleted item. Name: " + item.getName() + ". Id: " + item.getId());
                    Database.items.delete(item.getId());
                }
                itemAdapter.setEditingState(false);
            }
            if (!anySelected) {
                String text = "No items were selected.";
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }

            // hide buttons
            toggleVisibility();
        });

        // Find the userProfileImage view by its ID and set an OnClickListener
        CircleImageView userProfileImage = findViewById(R.id.userProfileImage);
        userProfileImage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, UserActivity.class)));

        // Register sort button to open sorting dialog
        findViewById(R.id.SortButton).setOnClickListener(v -> new SortDialog().show(getSupportFragmentManager(), SortDialog.TAG));

        
//        findViewById(R.id.FilterButton).setOnClickListener(v -> new FilterDialog().show(getSupportFragmentManager(), FilterDialog.TAG));

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

        }
    }

    /**
     * Toggles the visibility of the trash and edit tags buttons
     */
    @Override
    public void toggleVisibility() {
        if (editTagsButton != null && trashButton != null) {
            if (editTagsButton.getVisibility() == View.VISIBLE && trashButton.getVisibility() == View.VISIBLE) {
                editTagsButton.setVisibility(View.GONE);
                trashButton.setVisibility(View.GONE);
            } else {
                editTagsButton.setVisibility(View.VISIBLE);
                trashButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
