package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentResultListener;

import com.cmput301f23t28.casacatalog.Camera.BarcodeRecognition;
import com.cmput301f23t28.casacatalog.Camera.TextRecognitionHelper;
import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.ToolbarBuilder;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Photo;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;

/**
 * Activity for adding a new item to the inventory.
 * It allows users to enter item details, save the item to the database, and associate tags with the item.
 */
public class AddItemActivity extends AppCompatActivity implements AddPhotoFragment.OnFragmentInteractionListener {

    private Item newItem;
    private Bitmap photoBitmap;
    private boolean isBarcode;
    private ArrayList<Photo> photos = new ArrayList<>();

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
        ToolbarBuilder.create(this, getString(R.string.title_add_item));

        newItem = new Item();

        final Button addButton = findViewById(R.id.addItemToListBtn);
        final Button deleteButton = findViewById(R.id.deleteItemFromListBtn);
        final Button addPhotoButton = findViewById(R.id.addPhotoToItem);
        final Button addBarcodeButton = findViewById(R.id.BarcodeButton);

        // Set date preview to current date
        ((TextView)findViewById(R.id.purchaseDateText)).setText(newItem.getFormattedDate());

        hydrateTagList(newItem, findViewById(R.id.itemTagsList));

        // Remove deletebutton (only for editing not adding)
        ViewGroup layout = (ViewGroup) deleteButton.getParent();
        if (null != layout) //for safety only  as you are doing onClick
            layout.removeView(deleteButton);

        addPhotoButton.setOnClickListener(view -> {
            new AddPhotoFragment().show(getSupportFragmentManager(), "ADD_PHOTO");
        });

        addBarcodeButton.setOnClickListener(view -> {
            new AddPhotoFragment().show(getSupportFragmentManager(), "ADD_BARCODE");
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
            if (photos.size() > 0) {
                newItem.setPhotoURLs(photos);
            }

            Database.items.add(newItem);

            finish();
        });

        // Handles purchase date picker
        // If new item, initialize to current date
        findViewById(R.id.setDateButton).setOnClickListener(new ItemDatePicker(this, newItem, findViewById(R.id.purchaseDateText)));

        // Receives result from EditTagsActivity
        ActivityResultLauncher<Intent> editTagsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null) {
                            ArrayList<Tag> newTags = result.getData().getParcelableArrayListExtra("tags");
                            newItem.setTags(newTags);
                            hydrateTagList(newItem, findViewById(R.id.itemTagsList));
                        }
                    }
                }
        );
        // Add tag button that launches TagsActivity
        findViewById(R.id.addTagButton).setOnClickListener(view -> {
            Intent i = new Intent(this, EditTagsActivity.class);
            i.putExtra("tags", newItem.getTags());
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

    }

    /**
     * Receives back the URL of the photo in cloud storage to the activity.
     * @param input the URL of the photo.
     */
    @Override
    public void sendURL(String input) {
        Photo photo = new Photo(input);
        photos.add(photo);
        Log.d("PHOTOURL", "received " + input);
    }

    /**
     * Receives back the bitmap of the photo in local storage to the activity.
     * @param bitmap the bitmap of the photo.
     */
    @Override
    public void sendBitmap(Bitmap bitmap, boolean isBarcode) {
        //photoURLs.add(input);
        Log.d("PHOTO BITMAP", "received " + bitmap);
        photoBitmap = bitmap;
        this.isBarcode = isBarcode;
        if (isBarcode) {
            fillItemFromBarcode(bitmap);
        }
    }

    @Override
    public void onSerialNumberRecognized(String serialNumber) {
        newItem.setSerialNumber(serialNumber);
        TextInputLayout serialNumberInput = findViewById(R.id.itemSerialNumber);
        if (serialNumberInput != null && serialNumberInput.getEditText() != null) {
            serialNumberInput.getEditText().setText(serialNumber);
        }
    }

    /**
     * Gets a barcode image, and fills the item details on the add page according to it's
     * attributes inside of the barcode database
     * @param bitmap photo of the barcode.
     */
    private void fillItemFromBarcode(Bitmap bitmap) {

        // NOTE: this might not work because of 'scan barcode' logic
        // Not 100% sure how async functions work, or what the JSON it returns looks like
        BarcodeRecognition barcodeRecognition = new BarcodeRecognition(this);
        // Create an InputImage object from the Bitmap
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        // Now call the scanBarcodes method with this image
        newItem = barcodeRecognition.scanBarcodes(image);

        ((TextInputLayout) findViewById(R.id.itemName)).getEditText().setText(newItem.getName());
        ((TextInputLayout) findViewById(R.id.itemEstimatedValue)).getEditText().setText(newItem.getPrice().toString());

        // Is this part of it??
        /*
        TextRecognitionHelper textHelper = new TextRecognitionHelper(this);
        textHelper.recognizeTextFromImage();
        */

    }
}