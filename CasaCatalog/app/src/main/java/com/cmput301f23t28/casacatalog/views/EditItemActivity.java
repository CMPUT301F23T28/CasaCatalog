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
import com.cmput301f23t28.casacatalog.helpers.ToolbarBuilder;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity for editing an existing item. Inherits functionality from AddItemActivity
 * and repurposes it for editing items.
 */
public class EditItemActivity extends AppCompatActivity implements AddPhotoFragment.OnFragmentInteractionListener {

    private Item editingItem;

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

        this.editingItem = getIntent().getParcelableExtra("item");
        ToolbarBuilder.create(this, getString(R.string.title_edit_item, editingItem.getName()));

        // Setting the text of each of the 'EditText's to whatever the item's attributes are
        ((TextInputLayout) findViewById(R.id.itemName)).getEditText().setText(editingItem.getName());
        ((TextInputLayout) findViewById(R.id.itemEstimatedValue)).getEditText().setText(editingItem.getPrice().toString());
        ((TextView) findViewById(R.id.purchaseDateText)).setText(editingItem.getFormattedDate());
        ((TextInputLayout) findViewById(R.id.itemDescription)).getEditText().setText(editingItem.getDescription());
        ((TextInputLayout) findViewById(R.id.itemMake)).getEditText().setText(editingItem.getMake());
        ((TextInputLayout) findViewById(R.id.itemModel)).getEditText().setText(editingItem.getModel());
        ((TextInputLayout) findViewById(R.id.itemSerialNumber)).getEditText().setText(editingItem.getSerialNumber());
        ((TextInputLayout) findViewById(R.id.itemComments)).getEditText().setText(editingItem.getComment());

        hydrateTagList(editingItem, findViewById(R.id.itemTagsList));

        final Button editButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);
        final Button addPhotoButton = findViewById(R.id.addPhotoToItem);

        editButton.setText(R.string.item_edit_button_text);
        // Edits item in database, as well as on the item list displayed in MainActivity.

        editButton.setOnClickListener(view -> {
            TextInputLayout itemNameText = findViewById(R.id.itemName);
            if (itemNameText.getEditText().getText().toString() != null) {
                editingItem.setName(itemNameText.getEditText().getText().toString());
            }

            TextInputLayout itemValueText = findViewById(R.id.itemEstimatedValue);
            if (!itemValueText.getEditText().getText().toString().isEmpty()) {
                double price = Double.parseDouble(itemValueText.getEditText().getText().toString());
                editingItem.setPrice(price);
            }

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

                    /*
            // Set link to photo URL in cloud storage
            if (photoURLs.size() > 0) {
                editingItem.setPhotoURLs(photoURLs);
            }
            */

            Database.items.add(editingItem);
            // Send new item copy back
            Intent ret = new Intent();
            ret.putExtra("item", editingItem);
            setResult(Activity.RESULT_OK, ret);

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
                            hydrateTagList(editingItem, findViewById(R.id.itemTagsList));
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

    /**
     * Hydrates ChipGroup with the current item tags
     */
    private void hydrateTagList(Item item, ChipGroup group){
        // Preview tags as chips
        if(item.getTags() != null) {
            group.removeAllViews();
            for(Chip c : item.getTagsAsChips(this)) group.addView(c);
        }
    }

    @Override
    public void onOKPressed() {
        Toast.makeText(getApplicationContext(), "pressed", Toast.LENGTH_LONG);
    }

    /**
     * Sends back the URL of the photo in cloud storage to the activity.
     * @param input the URL of the photo.
     */
    @Override
    public void sendURL(String input) {
        Log.d("PHOTOURL", "Received " + input);
        List<String> photoURLs = editingItem.getPhotoURLsAsStrings();
        photoURLs.add(input);
        editingItem.setPhotoURLs(photoURLs);
    }

}
