package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddItemActivity  extends AppCompatActivity {

    private ItemHandler itemHandler;

    /*
    //Add when it's also in the database (not sure if it is)
    // This might not work I got crash when I put name + price here
    // instead of calling immediately upon click
    private TextInputEditText itemPurchaseDate = findViewById(R.id.itemPurchaseDate);
    private TextInputEditText itemDescription = findViewById(R.id.itemDescription);
    private TextInputEditText itemMake = findViewById(R.id.itemMake);
    private TextInputEditText itemModel = findViewById(R.id.itemModel);
    private TextInputEditText itemSerialNumber = findViewById(R.id.itemSerialNumber);
    private TextInputEditText itemComments = findViewById(R.id.itemComments);
    private TextInputEditText itemTags = findViewById(R.id.itemTags);
    */
    // Needs photos too (not on UI yet)
    Item newItem = new Item();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        // Need to reference the database from a different activity.
        // This is kind of a bad way of doing this but if itemhandler always references the same
        // database I don't think it should matter if I create a new one. Ideally this would
        // probably reference the same itemhandler as mainactivity but this should work
        itemHandler = new ItemHandler();

        findViewById(R.id.addItemToListBtn).setOnClickListener(view -> {
            // Feel free to get rid of below if it's not necessary (the javadoc)
            /**
             * Adds item to database, as well as the item list displayed in MainActivity.
             */
            TextInputLayout itemName = findViewById(R.id.itemName);
            newItem.setName(itemName.getEditText().getText().toString());
            // Should check if value is actually a double (probably possible in EditText somehow)
            TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
            if (itemValue.getEditText().getText().toString() != null) {
                double price = Double.parseDouble(itemValue.getEditText().getText().toString());
                newItem.setPrice(price);
            }

            // Add rest of attributes as well


            itemHandler.addItem(newItem);

            finish();
        });

        // Add tag button that launches TagsActivity
        // TODO: reuse this for when EditItemActivity is working
        findViewById(R.id.addTagButton).setOnClickListener(view -> {
            Intent i = new Intent(this, EditTagsActivity.class);
            i.putExtra("tags", newItem.getTags());
            // TODO: dont use deprecated method
            startActivityForResult(i, 200);
        });
    }

    // Receive data from EditTagsActivity
    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == 200) {  // TODO: enum instead of hardcoded id
            if (res == Activity.RESULT_OK) {
                // TODO: use parcelable instead of serializable
                ArrayList<Tag> newTags = (ArrayList<Tag>) data.getSerializableExtra("tags");
                // Add new tags to item, if we are editing one
                if (newItem != null) {
                    for (Tag tag : newTags) {
                        tag.setUses(tag.getUses() + 1); // TODO: decrease uses when tag is removed
                    }
                    newItem.setTags(newTags);
                }

            }

        }
    }
}