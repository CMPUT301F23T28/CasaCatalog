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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.ItemSorting;

/**
 * The sorting dialog fragment is presented when the user requests to sort the item list
 * It gives the options of what item property to sort by and in which order.
 */
public class FilterDialog extends DialogFragment {
    public static final String TAG = "DIALOG_FILTER";
    private static String sortByType = ItemSorting.Type.date.name();
    private static String sortOrder = ItemSorting.Order.descending.name();

    /**
     * Initializes the activity, registers listeners to allow inputting into spinners.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        getDialog().getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, 700);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_filter, null);

        Spinner typeSpinner = view.findViewById(R.id.spinner_filter_type);
        ArrayAdapter<CharSequence> typeSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.type_choices,
                R.layout.spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortByType = parent.getSelectedItem().toString();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner orderSpinner = view.findViewById(R.id.spinner_filter_comp);
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
        builder.setTitle("Filter by");  // TODO: extract to string resource file
        builder.setNegativeButton(getString(android.R.string.no), null);
        builder.setPositiveButton(getString(android.R.string.yes), (dialogInterface, i) -> {
            Database.items.sort(new ItemSorting(sortByType, sortOrder));
            Toast.makeText(this.getContext(), "Filtered items.", Toast.LENGTH_SHORT).show();
        });
        return builder.create();
    }
}