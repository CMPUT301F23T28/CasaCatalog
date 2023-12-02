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
import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.helpers.VisibilityCallback;
import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

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

        TextRecognitionHelper textHelper = new TextRecognitionHelper(this);
        textHelper.recognizeTextFromImage();

        // Record device identifier
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Initialize all databases
        Database.initialize();

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
        List<Item> test = Database.items.getItems();
        itemAdapter = new ItemListAdapter(this, Database.items.getItems(), editItemLauncher, this);
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);
        itemListView.setLayoutManager(new LinearLayoutManager(this));
        itemListView.setItemAnimator(null);     // fixes bug in Android
        Database.items.registerListener(itemAdapter, findViewById(R.id.InventoryValueNumber));

        // Sends the user to the 'add item' activity, allowing them to input their item and all of its relevant details.
        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AddItemActivity.class)));

        // handles deletion of selection items
        trashButton.setOnClickListener(v -> {
            boolean anySelected = false;
            for (Item item: Database.items.getItems()) {

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
