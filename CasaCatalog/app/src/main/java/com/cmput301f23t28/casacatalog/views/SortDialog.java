package com.cmput301f23t28.casacatalog.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;

public class SortDialog extends DialogFragment {
    public static final String TAG = "DIALOG_SORT";
    private static String sortByType = "Date";
    private static String sortOrder = "Descending";

    @Override @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sort, null);

        Spinner typeSpinner = view.findViewById(R.id.spinner_sort_type);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.type_choices,
                android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortByType = parent.getSelectedItem().toString();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner orderSpinner = view.findViewById(R.id.spinner_sort_order);
        ArrayAdapter<CharSequence> orderSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.order_choices,
                android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderSpinnerAdapter);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortOrder = parent.getSelectedItem().toString();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Creating dialog menu
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("Sort by");  // TODO: extract to string resource file
        builder.setNegativeButton(getString(android.R.string.no), null);
        builder.setPositiveButton(getString(android.R.string.yes), (dialogInterface, i) -> handleSort());
        return builder.create();
    }

    // TODO: handle sorting by more than just price
    private void handleSort(){
        Database.items.sort((a, b) -> {
            if (a.getPrice() == b.getPrice()) return 0;
            return a.getPrice() < b.getPrice() ? -1 : 1;
        });
        Toast.makeText(this.getContext(), sortByType + ", " + sortOrder, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this.getContext(), "Sorted items.", Toast.LENGTH_SHORT).show();
    }
}
