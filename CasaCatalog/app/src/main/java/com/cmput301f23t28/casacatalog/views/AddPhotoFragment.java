package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AddPhotoFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private ImageButton cameraButton;
    private ImageButton galleryButton;
    static final int CAMERA_CODE = 1;
    static final int GALLERY_CODE = 0;
    private Uri filePath; // filepath of image to be uploaded/selected
    private String currentPhotoPath;

    // instance for firebase storage and StorageReference (no idea if i need these here or in main?)
    FirebaseStorage storage;
    StorageReference storageReference;
    String photoURL;

    // To send string from fragment to activity (SO DIFFICULT FOR NO REASON)
    public interface OnOKListener {
        void sendURL(String input);
    }
    public OnFragmentInteractionListener mOnFragmentInteractionListener;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + "OnFragmentInteractionListener is not implemented");
        }

        try {
            mOnFragmentInteractionListener
                    = (OnFragmentInteractionListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e("PHOTO FRAGMENT", "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_photo, null);

        // Initialize storage reference
        storageReference = FirebaseStorage.getInstance().getReference();

        cameraButton = view.findViewById(R.id.camera_button);
        galleryButton = view.findViewById(R.id.gallery_button);


        cameraButton.setOnClickListener(v -> {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("IO ERROR", ex.toString());
            }
            if (photoFile != null) {
                filePath = FileProvider.getUriForFile(getActivity(),
                        "com.example.android.fileprovider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePath);
                // TODO: Don't use depreciated method ?
                startActivityForResult(cameraIntent, CAMERA_CODE);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

            /*
            if(CameraIntent.resolveActivity(getPackageManager()) != null){

            }*/
        });
        galleryButton.setOnClickListener(v -> {
            //Log.i("GalleryCode",""+GALLERY_CODE);
            Intent GalleryIntent = null;
            GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            GalleryIntent.setType("image/*");
            GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            // TODO: Don't use depreciated method ?
            startActivityForResult(GalleryIntent,GALLERY_CODE);
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add photo")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialog, which) -> {
//                    if (name.isEmpty() || province.isEmpty()) {
//                        return;
//                    }
                    listener.onOKPressed();
                }).create();
    }

    /**
     * Callback for the result from launching either the camera, or gallery application.
     * This method is invoked after the selecting an image, and returns the specified image.
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            switch(requestCode){
                case CAMERA_CODE:
                    Log.i("CameraCode","in activity result: "+CAMERA_CODE);
                    File f = new File(currentPhotoPath);
                    filePath = Uri.fromFile(f);
                    break;
                    //Bitmap resized = Bitmap.createScaledBitmap(bmp, bmpWidth,bmpHeight, true);
                    //mImageView.setImageBitmap(resized);

                case GALLERY_CODE:
                    Log.i("GalleryCode","in activity result: "+requestCode);
                    filePath = data.getData();
                    break;
                    //mImageView.setImageURI(filePath);
            }

            if (filePath != null) {
                Log.d("FILE_PATH", filePath.toString());
                // Code for showing progressDialog while uploading
                ProgressDialog progressDialog
                        = new ProgressDialog(getActivity());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                // Defining the child of storageReference
                StorageReference ref
                        = storageReference
                        .child(
                                "images/"
                                        + UUID.randomUUID().toString());

                // adding listeners on upload
                // or failure of image
                UploadTask uploadTask = ref.putFile(filePath);
//                        .addOnSuccessListener(
//                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
//
//                                    @Override
//                                    public void onSuccess(
//                                            UploadTask.TaskSnapshot taskSnapshot)
//                                    {
//
//                                        // Image uploaded successfully
//                                        // Dismiss dialog
//                                        progressDialog.dismiss();
//                                        Toast
//                                                .makeText(getActivity(),
//                                                        "Image Uploaded!!",
//                                                        Toast.LENGTH_SHORT)
//                                                .show();
//                                        getDialog().dismiss();
//                                    }
//                                })
//
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e)
//                            {
//
//                                // Error, Image not uploaded
//                                progressDialog.dismiss();
//                                Toast
//                                        .makeText(getActivity(),
//                                                "Failed " + e.getMessage(),
//                                                Toast.LENGTH_SHORT)
//                                        .show();
//                                getDialog().dismiss();
//                            }
//                        });
                // Get URL from cloud storage
                // Need to attach to item after uploading

                // Get the download URL
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            // The download URL of the image
                            progressDialog.dismiss();
                            Uri downloadUri = task.getResult();
                            photoURL = downloadUri.toString();
                            mOnFragmentInteractionListener.sendURL(photoURL);

                            Log.d("Download URL", photoURL);

                        } else {
                            // Handle failures
                            Log.e("Download URL", "Failed to get download URL");
                        }
                    }
                });

            }
            else {
                Toast
                    .makeText(getActivity(),
                            "Failed: Couldn't get file path.",
                            Toast.LENGTH_SHORT)
                    .show();
            }
        }
    }

    /**
     * Creates an image file after taking a picture, and stores the image capture in the location
     * created.
     * @return File of image captured.
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    public interface OnFragmentInteractionListener {
        void onOKPressed(/*City city*/);
        void sendURL(String URL);
    }

}
