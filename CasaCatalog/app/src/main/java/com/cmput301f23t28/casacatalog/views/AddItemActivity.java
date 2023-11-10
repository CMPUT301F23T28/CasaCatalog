package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
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
public class AddItemActivity  extends AppCompatActivity {

    private ItemHandler itemHandler;
    private Item newItem;

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
        // Need to reference the database from a different activity.
        // This is kind of a bad way of doing this but if itemhandler always references the same
        // database I don't think it should matter if I create a new one. Ideally this would
        // probably reference the same itemhandler as mainactivity but this should work
        itemHandler = new ItemHandler();
        Item newItem = new Item();

        final Button addButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);

        // Remove deletebutton (only for editing not adding)
        ViewGroup layout = (ViewGroup) deleteButton.getParent();
        if(null!=layout) //for safety only  as you are doing onClick
            layout.removeView(deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Adds item to database, as well as the item list displayed in MainActivity.
             */
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)

//                TextInputLayout itemName = findViewById(R.id.itemName);

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
                        try{
                            Date date = formatter.parse(dateValue.getEditText().getText().toString());
                            newItem.setDate(date);
                        } catch (ParseException e){
                            Log.e("ParseException", "ParseException"+ e.toString());
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


                    itemHandler.addItem(newItem);

                    finish();
                }
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

    /**
     * Callback for the result from launching EditTagsActivity.
     * This method is invoked after the EditTagsActivity finishes and returns the selected tags.
     *
     * @param req The integer request code originally supplied to startActivityForResult(),
     *            allowing you to identify who this result came from.
     * @param res The integer result code returned by the child activity through its setResult().
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

}