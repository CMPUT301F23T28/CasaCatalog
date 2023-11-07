package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.google.android.material.textfield.TextInputLayout;

public class EditTagsActivity extends AppCompatActivity {

    private ItemHandler itemHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tags);

        //final Button addButton = findViewById(R.id.addItemToListButton);

        addButton.setOnClickListener(view -> {
            Item newItem = new Item();
            TextInputLayout itemName = findViewById(R.id.itemName);
            newItem.setName(itemName.getEditText().getText().toString());
            // Should check if value is actually a double (probably possible in EditText somehow)
            TextInputLayout itemValue = findViewById(R.id.itemEstimatedValue);
            double price = Double.parseDouble(itemValue.getEditText().getText().toString());
            newItem.setPrice(price);
            // Add rest of attributes as well

            itemHandler.addItem(newItem);

            finish();
        });
    }



}
