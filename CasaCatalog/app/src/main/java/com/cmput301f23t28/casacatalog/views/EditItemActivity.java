package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.google.android.material.textfield.TextInputLayout;

public class EditItemActivity extends AddItemActivity {

    private ItemHandler itemHandler;
    private int listPosition;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            listPosition = extras.getInt("ITEM_POSITION");
            String itemName = extras.getString("ITEM_NAME");
            Double itemPrice = extras.getDouble("ITEM_PRICE");

            TextInputLayout itemNameText = findViewById(R.id.itemName);
            // Should check if value is actually a double (probably possible in EditText somehow)
            TextInputLayout itemValueText = findViewById(R.id.itemEstimatedValue);

            itemNameText.getEditText().setText(itemName);
            itemValueText.getEditText().setText(itemPrice.toString());
        }


        itemHandler = new ItemHandler();

        final Button editButton = findViewById(R.id.addItemToListBtn);

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
                // Should check if value is actually a double (probably possible in EditText somehow)
                TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
                double price = Double.parseDouble(itemValue.getEditText().getText().toString());
                newItem.setPrice(price);
                // Add rest of attributes as well (make model desc. comment etc)


                // This doesn't do what I thought
                // I"ll need to see the database to know if I modified the value
                // I don't (think) I have access to it right now
                //itemHandler.setItem(newItem, listPosition);

                finish();
            }
        });

    }
}
