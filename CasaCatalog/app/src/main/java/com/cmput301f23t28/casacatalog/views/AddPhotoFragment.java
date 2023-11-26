package com.cmput301f23t28.casacatalog.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301f23t28.casacatalog.R;

public class AddPhotoFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
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
