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

import java.text.SimpleDateFormat;

public class EditItemActivity extends AppCompatActivity {

    private ItemHandler itemHandler;
    private int listPosition;
    private Item editingItem;
    // Temporary solution so i dont convert string to date because im lazy
    private String stringItemDate;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        editingItem = new Item();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            listPosition = extras.getInt("ITEM_POSITION");
            editingItem.setName(extras.getString("ITEM_NAME"));
            editingItem.setPrice(extras.getDouble("ITEM_PRICE"));
            editingItem.setTags(extras.getString("ITEM_TAGS"));
            editingItem.setId(extras.getString("ITEM_ID"));
            stringItemDate = extras.getString("ITEM_DATE");

            /*
            if (itemID != null) {
                Log.d("ITEM ID", itemID);
            }
            else {
                Log.d("ITEM ID", "NULL UH OH");
            }*/

            TextInputLayout itemNameText = findViewById(R.id.itemName);
            // Should check if value is actually a double (probably possible in EditText somehow)
            TextInputLayout itemValueText = findViewById(R.id.itemEstimatedValue);
            TextInputLayout itemDateText = findViewById(R.id.itemPurchaseDate);
            TextInputLayout itemTagsText = findViewById(R.id.itemTags);

            itemNameText.getEditText().setText(editingItem.getName());
            itemValueText.getEditText().setText(editingItem.getPrice().toString());
            // SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy"); // IS BUGGING
            // TODO: Set this to a simple date not the whole thing
            itemDateText.getEditText().setText(stringItemDate);
            itemTagsText.getEditText().setText(editingItem.getTags()); // not working
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
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)
                /**
                 * Edits item in database, as well as on the item list displayed in MainActivity.
                 */
                Item newItem = new Item();
                TextInputLayout itemName = findViewById(R.id.itemName);
                newItem.setName(itemName.getEditText().getText().toString());
                // TODO: Should check if value is actually a double (probably possible in EditText somehow)
                TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
                double price = Double.parseDouble(itemValue.getEditText().getText().toString());
                newItem.setPrice(price);
                // Add rest of attributes as well (make model desc. comment etc)

                // Delete item from database, and add new item with new attributes
                itemHandler.deleteItem(editingItem.getId());
                // Now add new item
                itemHandler.addItem(editingItem);

                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)
                /**
                 * Deletes item from database, as well as on the item list displayed in MainActivity.
                 */

                itemHandler.deleteItem(editingItem.getId());

                finish();
            }
        });

    }
}
