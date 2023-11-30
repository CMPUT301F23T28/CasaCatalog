package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Activity for editing an existing item. Inherits functionality from AddItemActivity
 * and repurposes it for editing items.
 */
public class EditItemActivity extends AppCompatActivity implements AddPhotoFragment.OnFragmentInteractionListener {

    private int listPosition;
    private Item editingItem;

    TextInputLayout itemNameText;
    TextInputLayout itemValueText;
    TextInputLayout itemTagsText;
    TextInputLayout itemDescriptionText;
    TextInputLayout itemMakeText;
    TextInputLayout itemModelText;
    TextInputLayout itemSerialNumberText;
    TextInputLayout itemCommentText;

    /**
     * Called when the activity is starting. This is where most initialization should go:
     * calling setContentView(int) to inflate the activity's UI, using findViewById(int)
     * to programmatically interact with widgets in the UI, setting up listeners, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the most recent data supplied
     *                           in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        editingItem = new Item();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // I now realize this was a terrible way of doing this
            // But I'm too deep. Adds attributes to 'editingItem' for admittedly no reason
            // Since it gets overwritten afterwards anyways.
            listPosition = extras.getInt("ITEM_POSITION");
            editingItem.setName(extras.getString("ITEM_NAME"));
            editingItem.setPrice(extras.getDouble("ITEM_PRICE"));
            editingItem.setMake(extras.getString("ITEM_MAKE"));
            editingItem.setModel(extras.getString("ITEM_MODEL"));
            editingItem.setSerialNumber(extras.getString("ITEM_SERIAL_NUMBER"));
            editingItem.setComment(extras.getString("ITEM_COMMENT"));
            editingItem.setDescription(extras.getString("ITEM_DESCRIPTION"));
            editingItem.setTags(extras.getParcelableArrayList("ITEM_TAGS"));
            editingItem.setId(extras.getString("ITEM_ID"));
            editingItem.setDate((LocalDate) extras.get("ITEM_DATE"));

            // Setting all the 'EditText' thingies
            itemNameText = findViewById(R.id.itemName);
            // Should check if value is actually a double (probably possible in EditText somehow)
            itemValueText = findViewById(R.id.itemEstimatedValue);
            itemDescriptionText = findViewById(R.id.itemDescription);
            itemMakeText = findViewById(R.id.itemMake);
            itemModelText = findViewById(R.id.itemModel);
            itemSerialNumberText = findViewById(R.id.itemSerialNumber);
            itemCommentText = findViewById(R.id.itemComments);
            itemTagsText = findViewById(R.id.itemTags);

            // Setting the text of each of the 'EditText's to whatever the item's attributes are
            itemNameText.getEditText().setText(editingItem.getName());
            itemValueText.getEditText().setText(editingItem.getPrice().toString());
            if(editingItem.getDate() != null){
                ((TextView)findViewById(R.id.purchaseDateText)).setText(editingItem.getFormattedDate());
            }
            itemDescriptionText.getEditText().setText(editingItem.getDescription());
            itemMakeText.getEditText().setText(editingItem.getMake());
            itemModelText.getEditText().setText(editingItem.getModel());
            itemSerialNumberText.getEditText().setText(editingItem.getSerialNumber());
            itemCommentText.getEditText().setText(editingItem.getComment());
            if (editingItem.getComment() != null) {
                Log.d("ITEM_COMMENT_EDIT_ITEM", editingItem.getComment());
            }

            //itemTagsText.getEditText().setText(editingItem.getTags()); // not working
        }

        final Button editButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);
        final Button addPhotoButton = findViewById(R.id.addPhotoToItem);

        /*
        ViewGroup layout = (ViewGroup) deleteButton.getParent();
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        btnTag.setText("Delete Item");
        btnTag.setId(some_random_id);
        //add button to the layout
        layout.addView(btnTag);
        */

        editButton.setText(R.string.item_edit_button_text);
        // Edits item in database, as well as on the item list displayed in MainActivity.
        editButton.setOnClickListener(view -> {
            // Feel free to get rid of below if it's not necessary (the javadoc)

            // Add rest of attributes as well (make model desc. comment etc)

            // Delete item from database, and add new item with new attributes
            Database.items.delete(editingItem.getId());
            // Now add new item
            if (itemNameText.getEditText().getText().toString() != null) {
                editingItem.setName(itemNameText.getEditText().getText().toString());
            }

            // adds the price
            if (!itemValueText.getEditText().getText().toString().isEmpty()) {
                double price = Double.parseDouble(itemValueText.getEditText().getText().toString());
                editingItem.setPrice(price);
            }

            // Add rest of attributes as well
            TextInputLayout makeValue = findViewById(R.id.itemMake);
            if (makeValue.getEditText().getText().toString() != null) {
                editingItem.setMake(makeValue.getEditText().getText().toString());
            }

            TextInputLayout modelValue = findViewById(R.id.itemModel);
            if (modelValue.getEditText().getText().toString() != null) {
                editingItem.setModel(modelValue.getEditText().getText().toString());
            }

            TextInputLayout descriptionValue = findViewById(R.id.itemDescription);
            if (descriptionValue.getEditText().getText().toString() != null) {
                editingItem.setDescription(descriptionValue.getEditText().getText().toString());
            }

            TextInputLayout commentValue = findViewById(R.id.itemComments);
            if (commentValue.getEditText().getText().toString() != null) {
                editingItem.setComment(commentValue.getEditText().getText().toString());
            }

            TextInputLayout serialNumberValue = findViewById(R.id.itemSerialNumber);
            if (serialNumberValue.getEditText().getText().toString() != null) {
                editingItem.setSerialNumber(serialNumberValue.getEditText().getText().toString());
            }

            Database.items.add(editingItem);

            finish();
        });

        addPhotoButton.setOnClickListener(view -> {
            new AddPhotoFragment().show(getSupportFragmentManager(), "ADD_PHOTO");
        });

        // Deletes item from database, as well as on the item list displayed in MainActivity.
        deleteButton.setOnClickListener(view -> {
            Database.items.delete(editingItem.getId());
            finish();
        });

        findViewById(R.id.setDateButton).setOnClickListener(new ItemDatePicker(this, editingItem, findViewById(R.id.purchaseDateText)));

        // Receives result from EditTagsActivity
        ActivityResultLauncher<Intent> editTagsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            ArrayList<Tag> newTags = result.getData().getParcelableArrayListExtra("tags");
                            editingItem.setTags(newTags);
                        }
                    }
                }
        );
        // Add tag button that launches TagsActivity
        findViewById(R.id.addTagButton).setOnClickListener(view -> {
            Intent i = new Intent(this, EditTagsActivity.class);
            i.putExtra("tags", editingItem.getTags());
            editTagsLauncher.launch(i);
        });

    }

    @Override
    public void onOKPressed() {
        Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_LONG);
    }
}
