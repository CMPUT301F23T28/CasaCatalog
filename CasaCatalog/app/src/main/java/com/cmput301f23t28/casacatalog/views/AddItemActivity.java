package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
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

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;


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
    }



}
