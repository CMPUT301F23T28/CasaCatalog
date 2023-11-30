package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.textfield.TextInputLayout;

import org.checkerframework.common.returnsreceiver.qual.This;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Activity for adding a new item to the inventory.
 * It allows users to enter item details, save the item to the database, and associate tags with the item.
 */
public class AddItemActivity extends AppCompatActivity {

    private Item newItem;

    /**
     * Called when the activity is starting. This method is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, and setting up listeners.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the most recent data supplied
     *                           in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        newItem = new Item();

        final Button addButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);

        // Set date preview to current date
        ((TextView)findViewById(R.id.purchaseDateText)).setText(newItem.getFormattedDate());

        // Remove deletebutton (only for editing not adding)
        ViewGroup layout = (ViewGroup) deleteButton.getParent();
        if (null != layout) //for safety only  as you are doing onClick
            layout.removeView(deleteButton);

        // Adds item to database, as well as the item list displayed in MainActivity.
        addButton.setOnClickListener(view -> {
            TextInputLayout nameValue = findViewById(R.id.itemName);
            if (nameValue.getEditText().getText().toString() != null) {
                newItem.setName(nameValue.getEditText().getText().toString());
            }

            // adds the price
            TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
            if (!itemValue.getEditText().getText().toString().isEmpty()) {
                double price = Double.parseDouble(itemValue.getEditText().getText().toString());
                newItem.setPrice(price);
            }

            // Add rest of attributes as well
            TextInputLayout makeValue = findViewById(R.id.itemMake);
            if (makeValue.getEditText().getText().toString() != null) {
                newItem.setMake(makeValue.getEditText().getText().toString());
            }

            TextInputLayout modelValue = findViewById(R.id.itemModel);
            if (modelValue.getEditText().getText().toString() != null) {
                newItem.setModel(modelValue.getEditText().getText().toString());
            }

            TextInputLayout descriptionValue = findViewById(R.id.itemDescription);
            if (descriptionValue.getEditText().getText().toString() != null) {
                newItem.setDescription(descriptionValue.getEditText().getText().toString());
            }

            TextInputLayout commentValue = findViewById(R.id.itemComments);
            if (commentValue.getEditText().getText().toString() != null) {
                newItem.setComment(commentValue.getEditText().getText().toString());
            }

            TextInputLayout serialNumberValue = findViewById(R.id.itemSerialNumber);
            if (serialNumberValue.getEditText().getText().toString() != null) {
                newItem.setSerialNumber(serialNumberValue.getEditText().getText().toString());
            }

            Database.items.add(newItem);

            finish();
        });

        // Handles purchase date picker
        // If new item, initialize to current date
        findViewById(R.id.setDateButton).setOnClickListener(new ItemDatePicker(this, newItem, findViewById(R.id.purchaseDateText)));

        // Receives result from EditTagsActivity
        ActivityResultLauncher<Intent> editTagsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            ArrayList<Tag> newTags = result.getData().getParcelableArrayListExtra("tags");
                            newItem.setTags(newTags);
                        }
                    }
                }
        );
        // Add tag button that launches TagsActivity
        findViewById(R.id.addTagButton).setOnClickListener(view -> {
            Intent i = new Intent(this, EditTagsActivity.class);
            i.putExtra("tags", newItem.getTags());
            editTagsLauncher.launch(i);
        });
    }
}