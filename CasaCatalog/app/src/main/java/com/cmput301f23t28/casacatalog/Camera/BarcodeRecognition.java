package com.cmput301f23t28.casacatalog.Camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.List;

/**
 * An AppCompatActivity class for recognizing barcodes in images.
 */
public class BarcodeRecognition extends AppCompatActivity {

    private Context context;
    private String barcodeNumber;

    /**
     * Constructor for BarcodeRecognition.
     *
     * @param context The context where this class is being used.
     */
    public BarcodeRecognition(Context context) {
        this.context = context;
    }

    /**
     * Retrieves the barcode number scanned.
     *
     * @return The scanned barcode number as a String.
     */
    public String getBarcodeNumber() {
        return barcodeNumber;
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
        BarcodeScannerOptions options =
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

        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        for (Barcode barcode : barcodes) {
                            barcodeNumber = barcode.getRawValue();
                            Log.d("BarcodeValue", "Barcode value: " + barcodeNumber);
                            // Optionally update the UI, such as displaying the barcode value in a TextView
                            // textView.setText(rawValue);
                        }

                        new FetchProductDetails(barcodeNumber, newItem).execute();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e("Barcode Scanning", "Error scanning barcodes", e);
                    }
                });
        return newItem;
    }
}
