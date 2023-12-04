package com.cmput301f23t28.casacatalog.views;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Filter;
import com.cmput301f23t28.casacatalog.helpers.FilterAdapter;
import com.cmput301f23t28.casacatalog.helpers.ItemSorting;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * The sorting dialog fragment is presented when the user requests to sort the item list
 * It gives the options of what item property to sort by and in which order.
 */
public class FilterDialog extends DialogFragment {
    public static final String TAG = "DIALOG_FILTER";
    private static String filterByType = ItemSorting.Type.date.name();
    private static String filterCom = ItemSorting.Order.descending.name();
    FilterAdapter itemAdapter;
    RecyclerView itemListView;
    List<Filter> filters;


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
//        View viewItem = LayoutInflater.from(getActivity()).inflate(R.layout.filter_item, null);


        ArrayList items = new ArrayList<>();
        itemAdapter =  new FilterAdapter(filters);
        itemListView = view.findViewById(R.id.filter_list);
        itemListView.setAdapter(itemAdapter);
        itemListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        itemListView.setItemAnimator(null);     // fixes bug in Android
//        Database.items.registerListener(itemAdapter, view.findViewById(R.id.InventoryValueNumber));

        TextInputLayout inputValue1 = view.findViewById(R.id.filter_value_1);
        TextInputLayout inputValue2 = view.findViewById(R.id.filter_value_2);

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
                filterByType = parent.getSelectedItem().toString();
                if (filterByType.toLowerCase().equals("date")){
                    inputValue2.setVisibility(View.VISIBLE);
                } else{
                    inputValue2.setVisibility(View.GONE);
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        Spinner filterSpinner = view.findViewById(R.id.spinner_filter_comp);
        ArrayAdapter<CharSequence> orderSpinnerAdapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.filter_comp_choices,
                android.R.layout.simple_spinner_item);
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(orderSpinnerAdapter);
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterCom = parent.getSelectedItem().toString();
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

//        TextInputLayout inputValue2 = view.findViewById(R.id.filter_input_value_2);
        // Creating dialog menu
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("Filter by");  // TODO: extract to string resource file
        builder.setNegativeButton(getString(android.R.string.no), null);
        builder.setPositiveButton(getString(android.R.string.yes), (dialogInterface, i) -> {
            String val1Str = inputValue1.getEditText().getText().toString();
            String val2Str = inputValue2.getEditText().getText().toString();
            Database.items.filter(new Filter(filterByType, filterCom, val1Str, val2Str));
            Toast.makeText(this.getContext(), "Filtered items.", Toast.LENGTH_SHORT).show();
        });
        return builder.create();
    }
}