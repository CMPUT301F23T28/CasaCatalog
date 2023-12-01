package com.cmput301f23t28.casacatalog.Camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.util.SparseIntArray;
import android.view.Surface;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class BarcodeRecognition {

    //If we use CameraX
//    private class YourAnalyzer implements ImageAnalysis.Analyzer {
//
//        @Override
//        public void analyze(ImageProxy imageProxy) {
//            Image mediaImage = imageProxy.getImage();
//            if (mediaImage != null) {
//                InputImage image =
//                        InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
//                // Pass image to an ML Kit Vision API
//                // ...
//            }
//        }
//    }
//

    /**
     * Create input image object and image's rotation
     */
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing) throws CameraAccessException {
            // Get the device's current rotation relative to its "native" orientation.
            // Then, from the ORIENTATIONS table, look up the angle the image must be
            // rotated to compensate for the device's rotation.
            int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
            int rotationCompensation = ORIENTATIONS.get(deviceRotation);

            // Get the device's sensor orientation.
            CameraManager cameraManager = (CameraManager) activity.getSystemService(CAMERA_SERVICE);
            int sensorOrientation = cameraManager
                    .getCameraCharacteristics(cameraId)
                    .get(CameraCharacteristics.SENSOR_ORIENTATION);

            if (isFrontFacing) {
                rotationCompensation = (sensorOrientation + rotationCompensation) % 360;
            } else { // back-facing
                rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360;
            }
            return rotationCompensation;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)



    //pass the media.Image object and the rotation degree value
    public void processImage(Bitmap bitmap, int rotationDegree, Activity activity) {
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);

        BarcodeScanner scanner = BarcodeScanning.getClient();
        // Or, to specify the formats to recognize:
        // BarcodeScanner scanner = BarcodeScanning.getClient(options);
    }
    /**
     * Get Instance of barcode scanner
     */
    Task<List<Barcode>> result = scanner.process(image)
            .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                @Override
                public void onSuccess(List<Barcode> barcodes) {
                    // Task completed successfully
                    // ...
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Task failed with an exception
                    // ...
                }
            });

    /**
     * Get information from barcode
     */

    for (Barcode barcode: barcodes) {
        Rect bounds = barcode.getBoundingBox();
        Point[] corners = barcode.getCornerPoints();

        String rawValue = barcode.getRawValue();

        int valueType = barcode.getValueType();
        // See API reference for complete list of supported types
        switch (valueType) {
            case Barcode.TYPE_WIFI:
                String ssid = barcode.getWifi().getSsid();
                String password = barcode.getWifi().getPassword();
                int type = barcode.getWifi().getEncryptionType();
                break;
            case Barcode.TYPE_URL:
                String title = barcode.getUrl().getTitle();
                String url = barcode.getUrl().getUrl();
                break;
        }
    }

}
