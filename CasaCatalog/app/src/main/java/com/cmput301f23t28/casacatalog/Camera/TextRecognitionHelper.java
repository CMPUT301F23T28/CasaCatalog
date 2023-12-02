package com.cmput301f23t28.casacatalog.Camera;

import static android.content.Context.CAMERA_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.cmput301f23t28.casacatalog.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;


/**
 * TextRecognitionHelper provides functionalities for text recognition in images using
 * Google's ML Kit Text Recognition API.
 * <p>
 * This class includes methods to handle text recognition from images, considering
 * the device's orientation and camera characteristics. It is designed to be used
 * within an Android application context.
 */
public class TextRecognitionHelper {
    private Context context;
    private TextRecognizer recognizer;

    /**
     * Constructor for TextRecognitionHelper.
     *
     * @param context The Android context, usually provided by an Activity or Application.
     */
    public TextRecognitionHelper(Context context) {
        this.context = context;
        this.recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
    }


    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 0);
        ORIENTATIONS.append(Surface.ROTATION_90, 90);
        ORIENTATIONS.append(Surface.ROTATION_180, 180);
        ORIENTATIONS.append(Surface.ROTATION_270, 270);
    }

    /**
     * Calculates the rotation compensation for an image based on the device's current orientation.
     *
     * @param cameraId       The ID of the camera being used.
     * @param activity       The activity context, used to access system services.
     * @param isFrontFacing  Boolean indicating if the camera is front-facing.
     * @return The rotation compensation angle in degrees.
     * @throws CameraAccessException If the camera ID is not valid or access is not available.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, boolean isFrontFacing)
            throws CameraAccessException {
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

    /**
     * Determines the current rotation degree of the device.
     *
     * @return The rotation degree (0, 90, 180, 270) of the device.
     */
    private int getRotationDegree() {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int deviceRotation = windowManager.getDefaultDisplay().getRotation();
        return ORIENTATIONS.get(deviceRotation);
    }

    /**
     * Recognizes and processes text from a predefined image resource.
     * <p>
     * This method handles text recognition from an image, processes the recognized text,
     * and logs the results. It uses a sample image resource defined in R.drawable.sample_serial.
     */
    public void recognizeTextFromImage() {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sample_serial);
        int rotationDegree = getRotationDegree();
        InputImage image = InputImage.fromBitmap(bitmap, rotationDegree);
        // Process the image
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(new OnSuccessListener<Text>() {
                            @Override
                            public void onSuccess(Text visionText) {
                                // Task completed successfully
                                StringBuilder sb = new StringBuilder();
                                for (Text.TextBlock block : visionText.getTextBlocks()) {
                                    // Extract the text from the block
                                    String blockText = block.getText();
                                    sb.append(blockText).append("\n");

                                    // Optionally, iterate through lines and elements if more structure is needed
                                    for (Text.Line line : block.getLines()) {
                                        String lineText = line.getText();
                                        // Process line text or elements within the line if needed
                                        for (Text.Element element : line.getElements()) {
                                            String elementText = element.getText();
                                            // Process element text if needed
                                        }
                                    }
                                }
                                // Here, 'sb.toString()' contains the full extracted text
                                processExtractedText(sb.toString());
                            }
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                    }
                                });
    }

    /**
     * Processes and logs the extracted text from the image.
     *
     * @param text The extracted text from the image.
     */
    private void processExtractedText(String text) {
        Log.d("TextRecognition", "Text From Image: " + text);
    }

    public interface TextRecognitionListener {
        void onTextRecognized(String recognizedText);
    }

    public void recognizeTextFromBitmap(Bitmap bitmap, TextRecognitionListener listener) {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        recognizer.process(image)
                .addOnSuccessListener(visionText -> {
                    StringBuilder extractedText = new StringBuilder();
                    for (Text.TextBlock block : visionText.getTextBlocks()) {
                        extractedText.append(block.getText()).append("\n");
                    }
                    listener.onTextRecognized(extractedText.toString().trim());
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    listener.onTextRecognized("");
                });
    }

}
