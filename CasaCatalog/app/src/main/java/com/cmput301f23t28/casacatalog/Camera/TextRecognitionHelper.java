package com.cmput301f23t28.casacatalog.Camera;

import android.graphics.Point;
import android.graphics.Rect;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;



public class TextRecognitionHelper {

    // Create an instance of TextRecognizer
    TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);

    /*
    Image implementation depends on format of photos

    Either one of
    - media.Image
    - file URI
    - ByteBuffer
    - ByteArray
    - Bitmap
     */

    // Prepare the Input Image

    // Process the image
//    Task<Text> result =
//            recognizer.process(image)
//                    .addOnSuccessListener(new OnSuccessListener<Text>() {
//                        @Override
//                        public void onSuccess(Text visionText) {
//                            // Task completed successfully
//                            // ...
//                        }
//                    })
//                    .addOnFailureListener(
//                            new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    // Task failed with an exception
//                                    // ...
//                                }
//                            });

    // Extract text from blocks of recognized text
//    String resultText = result.getText();
//    for (Text.TextBlock block : result.getTextBlocks()) {
//            String blockText = block.getText();
//            Point[] blockCornerPoints = block.getCornerPoints();
//            Rect blockFrame = block.getBoundingBox();
//            for (Text.Line line : block.getLines()) {
//                String lineText = line.getText();
//                Point[] lineCornerPoints = line.getCornerPoints();
//                Rect lineFrame = line.getBoundingBox();
//                for (Text.Element element : line.getElements()) {
//                    String elementText = element.getText();
//                    Point[] elementCornerPoints = element.getCornerPoints();
//                    Rect elementFrame = element.getBoundingBox();
//                    for (Text.Symbol symbol : element.getSymbols()) {
//                        String symbolText = symbol.getText();
//                        Point[] symbolCornerPoints = symbol.getCornerPoints();
//                        Rect symbolFrame = symbol.getBoundingBox();
//                    }
//                }
//            }
//        }



}
