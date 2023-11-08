package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        // Need to reference the database from a different activity.
        // This is kind of a bad way of doing this but if itemhandler always references the same
        // database I don't think it should matter if I create a new one. Ideally this would
        // probably reference the same itemhandler as mainactivity but this should work
        itemHandler = new ItemHandler();

        final Button addButton = findViewById(R.id.addItemToListBtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Feel free to get rid of below if it's not necessary (the javadoc)
                /**
                 * Adds item to database, as well as the item list displayed in MainActivity.
                 */
                Item newItem = new Item();
                TextInputLayout itemName = findViewById(R.id.itemName);
                newItem.setName(itemName.getEditText().getText().toString());
                // Should check if value is actually a double (probably possible in EditText somehow)
                TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
                if (itemValue.getEditText().getText().toString() != null){
                    double price = Double.parseDouble(itemValue.getEditText().getText().toString());
                    newItem.setPrice(price);
                }

                // Add rest of attributes as well

                itemHandler.addItem(newItem);

                finish();
            }
        });
    }



}
