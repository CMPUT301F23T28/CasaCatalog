package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Filter;
import com.cmput301f23t28.casacatalog.helpers.FilterAdapter;
import com.cmput301f23t28.casacatalog.helpers.ToolbarBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * The filter activity is presented when the user requests to filter the item list
 * It allows adding multiple filters and adjusting their options.
 */
public class FilterActivity extends AppCompatActivity{
    ArrayList<Filter> filters;
    FilterAdapter filterListAdapter;
    RecyclerView filterListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter);
        filters = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Filter> list = bundle.getParcelableArrayList("filters");
            filters.clear();
            // Use the list as needed
            for (int i =0; i < list.size(); i++){
                filters.add(list.get(i));
            }
        }

        filterListAdapter = new FilterAdapter(filters);
        filterListView = findViewById(R.id.filter_list);
        filterListView.setAdapter(filterListAdapter);
        filterListView.setLayoutManager(new LinearLayoutManager(this));
        filterListView.setItemAnimator(null);     // fixes bug in Android


        final ExtendedFloatingActionButton addButton = findViewById(R.id.add_filter_button);
        addButton.setOnClickListener(v -> {
            filters.add(new Filter());
            filterListAdapter.notifyItemInserted(filters.size()-1);
            Log.e("Filter","adding a new item " + filterListAdapter.getItemCount());
        });

        ToolbarBuilder.create(this, "Filter", view -> {
            boolean error_recieved = false;
            ArrayList<Filter> filterList= new ArrayList<>();
            for (int i = 0; i < filterListAdapter.getItemCount(); i++) {
                FilterAdapter.MyViewHolder holder =
                        (FilterAdapter.MyViewHolder) filterListView.findViewHolderForAdapterPosition(i);
                if (holder != null) {
                    Filter dataItem = holder.getFilter();
                    if (dataItem.getVal1().equals("")) {
                        Toast.makeText(FilterActivity.this, "value 1 can not be empty",
                                Toast.LENGTH_LONG).show();
                        error_recieved = true;
                    } else if (dataItem.getCurrentType().toString().toLowerCase().equals("date") &&
                            dataItem.getCurrentFilterType().toString().toLowerCase().equals(
                                    "between") && dataItem.getVal2().equals("")) {
                        Toast.makeText(FilterActivity.this, "value 2 can not be empty", Toast.LENGTH_LONG).show();
                        error_recieved = true;
                    } else if(dataItem.getCurrentType().toString().toLowerCase().equals("value")
                            && !dataItem.getVal1().matches("[-+]?\\d*\\.?\\d+")){
                        Toast.makeText(FilterActivity.this, "value 1 is not numeric",
                                Toast.LENGTH_LONG).show();
                        error_recieved = true;
                    } else if(dataItem.getCurrentType().toString().toLowerCase().equals("date")){

                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                            LocalDate startDate = LocalDate.parse(dataItem.getVal1(), formatter);
                        }catch (Exception e){
                            error_recieved = true;
                        }
                        if (error_recieved){
                            Toast.makeText(FilterActivity.this, "value 1 is not date in format dd/MM/yyyy",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    if (dataItem.getCurrentType().toString().toLowerCase().equals("date") &&
                            dataItem.getCurrentFilterType().toString().toLowerCase().equals("between")){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        try {
                            LocalDate startDate = LocalDate.parse(dataItem.getVal1(), formatter);
                        }catch (Exception e){
                            Toast.makeText(FilterActivity.this, "value 1 is not date in format dd/MM/yyyy",
                                    Toast.LENGTH_LONG).show();
                            error_recieved = true;
                        }
                        try {
                            LocalDate startDate = LocalDate.parse(dataItem.getVal2(), formatter);
                        }catch (Exception e){
                            Toast.makeText(FilterActivity.this, "value 2 is not date in format " +
                                            "dd/MM/yyyy",
                                    Toast.LENGTH_LONG).show();
                            error_recieved = true;
                        }
                    }

                    if (!error_recieved){
                        filterList.add(dataItem);

                    }
                }
            }
            if (!error_recieved){
                // Send new filter copy back
                Bundle bundle_send = new Bundle();
                bundle_send.putParcelableArrayList("filters", filterList);
                Intent i = new Intent(FilterActivity.this, MainActivity.class);
                i.putExtras(bundle_send);
                startActivity(i);
            }
        });
    }
}
