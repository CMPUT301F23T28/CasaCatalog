package com.cmput301f23t28.casacatalog.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;

public class AddPhotoFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private ImageButton cameraButton;
    private ImageButton galleryButton;
    static final int CAMERA_CODE = 1;
    static final int GALLERY_CODE = 0;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context + "OnFragmentInteractionListener is not implemented");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_photo, null);

        cameraButton = view.findViewById(R.id.camera_button);
        galleryButton = view.findViewById(R.id.gallery_button);

        cameraButton.setOnClickListener(v -> {
            Intent CameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(CameraIntent, CAMERA_CODE);
            /*
            if(CameraIntent.resolveActivity(getPackageManager()) != null){

            }*/
        });
        galleryButton.setOnClickListener(v -> {
            Log.i("GalleryCode",""+GALLERY_CODE);
            Intent GalleryIntent = null;
            GalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            GalleryIntent.setType("image/*");
            GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
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
                    Toast.makeText(getActivity(), "hello", Toast.LENGTH_LONG).show();
                    listener.onOKPressed(/* new City(name, province) */);
                }).create();
    }

    public interface OnFragmentInteractionListener {
        void onOKPressed(/*City city*/);
    }
}
