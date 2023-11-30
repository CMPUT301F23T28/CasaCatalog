package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Activity for adding a new item to the inventory.
 * It allows users to enter item details, save the item to the database, and associate tags with the item.
 */
public class AddItemActivity extends AppCompatActivity implements AddPhotoFragment.OnFragmentInteractionListener {

    private Item newItem;
    private String photoURL;

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
        final Button addPhotoButton = findViewById(R.id.addPhotoToItem);

        // Remove deletebutton (only for editing not adding)
        ViewGroup layout = (ViewGroup) deleteButton.getParent();
        if (null != layout) //for safety only  as you are doing onClick
            layout.removeView(deleteButton);

        addPhotoButton.setOnClickListener(view -> {
            new AddPhotoFragment().show(getSupportFragmentManager(), "ADD_PHOTO");
        });

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

            // adds the date
            TextInputLayout dateValue = findViewById(R.id.itemPurchaseDate);
            if (!dateValue.getEditText().getText().toString().isEmpty()) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                try {
                    Date date = formatter.parse(dateValue.getEditText().getText().toString());
                    newItem.setDate(date);
                } catch (ParseException e) {
                    Log.e("ParseException", "ParseException" + e.toString());
                }
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

            // Set link to photo URL in cloud storage
            newItem.setPhotoURL(photoURL);

            Database.items.add(newItem);

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

    /**
     * Callback for the result from launching EditTagsActivity.
     * This method is invoked after the EditTagsActivity finishes and returns the selected tags.
     *
     * @param req  The integer request code originally supplied to startActivityForResult(),
     *             allowing you to identify who this result came from.
     * @param res  The integer result code returned by the child activity through its setResult().
     * @param data An Intent, which can return result data to the caller (various data can be attached to Intent "extras").
     */
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

    @Override
    public void onOKPressed() {
        Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_LONG);
    }

    /**
     * Receives back the URL of the photo in cloud storage to the activity.
     * @param input the URL of the photo.
     */
    @Override
    public void sendURL(String input) {
        photoURL = input;
    }
}