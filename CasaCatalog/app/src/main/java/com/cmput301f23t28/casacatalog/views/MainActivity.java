package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The main activity that starts when the application is launched.
 * It initializes the database, item handler, and sets up the main user interface,
 * including the RecyclerView for items and listeners for UI elements.
 */
public class MainActivity extends AppCompatActivity {
    public static String deviceId;
    private ArrayList<Item> itemList;
    private RecyclerView itemListView;
    private TextView totalValueTextView;

    private ItemListAdapter itemAdapter;

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

        // Record device identifier
        deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        // Initialize all databases
        Database.initialize();

        // If first time using app, start create user activity
        Database.users.getCollection().document(deviceId).get().addOnCompleteListener(task -> {
            if( !task.isSuccessful() || !task.getResult().exists() ){
                startActivity(new Intent(this, NewUserActivity.class));
            }
        });

        itemAdapter = new ItemListAdapter(this, Database.items.getItems(), Database.items.getCollection());
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);
        itemListView.setLayoutManager(new LinearLayoutManager(this));
        Database.items.registerListener(itemAdapter);

        String totalValueFormatted = String.format(Locale.ENGLISH, "$%.2f", Database.items.getTotalValue());
        ((TextView)findViewById(R.id.InventoryValueNumber)).setText(totalValueFormatted);

        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Sends the user to the 'add item' activity, allowing them to input their item
                 * and all of its relevant details.
                 */

                Intent addItemActivityIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(addItemActivityIntent);

            }
        });

        // Find the userProfileImage view by its ID and set an OnClickListener
        CircleImageView userProfileImage = findViewById(R.id.userProfileImage);
        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserActivity
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

    }
}
