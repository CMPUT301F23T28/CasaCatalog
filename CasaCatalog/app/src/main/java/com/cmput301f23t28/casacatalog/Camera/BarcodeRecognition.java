package com.cmput301f23t28.casacatalog.Camera;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.helpers.BarcodeCallback;
import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

/**
 * An AppCompatActivity class for recognizing barcodes in images.
 */
public class BarcodeRecognition extends AppCompatActivity {

    private String barcodeNumber;
    private BarcodeCallback callback;


    /**
     * Constructor for BarcodeRecognition.
     *
     * @param context The context where this class is being used.
     */
    public BarcodeRecognition(Context context, BarcodeCallback callback) {
        this.callback = callback;
    }

    /**
     * Initializes the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down, this Bundle contains the most recent data supplied
     *                           in onSaveInstanceState(Bundle). Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Scans barcodes from the given image and processes them.
     * On success, updates the barcodeNumber field and optionally updates the UI.
     * On failure, logs an error.
     *
     * @param image The InputImage to scan barcodes from.
     */
    public Item scanBarcodes(InputImage image) {
        Item newItem = new Item();
        new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_QR_CODE,
                        Barcode.FORMAT_AZTEC,
                        Barcode.FORMAT_UPC_A,
                        Barcode.FORMAT_UPC_E)
                .build();

        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);

        scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        for (Barcode barcode : barcodes) {
                            barcodeNumber = barcode.getRawValue();
                            Log.d("BarcodeValue", "Barcode value: " + barcodeNumber);
                            // Optionally update the UI, such as displaying the barcode value in a TextView
                            // textView.setText(rawValue);
                        }

                        // Inside the onSuccess method of scanBarcodes
                        new FetchProductDetails(barcodeNumber, newItem, new FetchProductDetails.FetchProductDetailsCallback() {
                            @Override
                            public void onDetailsFetched(Item newItem) {
                                // Handle the fetched details
                                // You can update UI or do any other processing here
                                callback.onBarcodeScanned(newItem);
                            }

                            @Override
                            public void onDetailsFetchFailed(String errorMessage) {
                                // Handle the case where fetching details failed
                                Log.e("FetchProductDetails", "Failed: " + errorMessage);
                            }
                        }).execute();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e("Barcode Scanning", "Error scanning barcodes", e);
                        callback.onBarcodeScanFailed("Failed to scan barcode.");
                    }
                });
        return newItem;
    }
}
