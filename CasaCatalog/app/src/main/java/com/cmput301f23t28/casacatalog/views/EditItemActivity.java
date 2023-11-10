package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
    /**
     * Activity for editing an existing item. Inherits functionality from AddItemActivity
     * and repurposes it for editing items.
     */
public class EditItemActivity extends AppCompatActivity {

    private ItemHandler itemHandler;
    private int listPosition;
    private Item editingItem;
    // Temporary solution so i dont convert string to date because im lazy
    private String stringItemDate;

    TextInputLayout itemNameText;
    TextInputLayout itemValueText;
    TextInputLayout itemDateText;
    TextInputLayout itemTagsText;
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
            listPosition = extras.getInt("ITEM_POSITION");
            editingItem.setName(extras.getString("ITEM_NAME"));
            editingItem.setPrice(extras.getDouble("ITEM_PRICE"));
            // (Max) tags was changed since I worked on this, will have to fix later.
            // editingItem.setTags(extras.getString("ITEM_TAGS"));
            editingItem.setId(extras.getString("ITEM_ID"));
            stringItemDate = extras.getString("ITEM_DATE");

            /*
            if (itemID != null) {
                Log.d("ITEM ID", itemID);
            }
            else {
                Log.d("ITEM ID", "NULL UH OH");
            }*/

            itemNameText = findViewById(R.id.itemName);
            // Should check if value is actually a double (probably possible in EditText somehow)
            itemValueText = findViewById(R.id.itemEstimatedValue);
            itemDateText = findViewById(R.id.itemPurchaseDate);
            itemTagsText = findViewById(R.id.itemTags);

            itemNameText.getEditText().setText(editingItem.getName());
            itemValueText.getEditText().setText(editingItem.getPrice().toString());
            // SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy"); // IS BUGGING
            // TODO: Set this to a simple date not the whole thing
            itemDateText.getEditText().setText(stringItemDate);
            //itemTagsText.getEditText().setText(editingItem.getTags()); // not working
        }


        itemHandler = new ItemHandler();

        final Button editButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);

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
        editButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Edits item in database, as well as on the item list displayed in MainActivity.
             */
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)

                // Add rest of attributes as well (make model desc. comment etc)

                // Delete item from database, and add new item with new attributes
                itemHandler.deleteItem(editingItem.getId());
                // Now add new item
                if (itemNameText.getEditText().getText().toString() != null) {
                    editingItem.setName(itemNameText.getEditText().getText().toString());
                }


                // adds the price
                if (!itemValueText.getEditText().getText().toString().isEmpty()) {
                    double price = Double.parseDouble(itemValueText.getEditText().getText().toString());
                    editingItem.setPrice(price);
                }

                // adds the date
                if (!itemDateText.getEditText().getText().toString().isEmpty()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                    try{
                        Date date = formatter.parse(itemDateText.getEditText().getText().toString());
                        editingItem.setDate(date);
                    } catch (ParseException e){
                        Log.e("ParseException", "ParseException"+ e.toString());
                    }

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

                itemHandler.addItem(editingItem);

                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Deletes item from database, as well as on the item list displayed in MainActivity.
             */
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)


                itemHandler.deleteItem(editingItem.getId());

                finish();
            }
        });

    }
}
