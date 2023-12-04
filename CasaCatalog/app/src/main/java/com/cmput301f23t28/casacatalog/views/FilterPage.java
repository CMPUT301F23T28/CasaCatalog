package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.helpers.Filter;
import com.cmput301f23t28.casacatalog.helpers.FilterAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * The main activity that starts when the application is launched.
 * It initializes the database, item handler, and sets up the main user interface,
 * including the RecyclerView for items and listeners for UI elements.
 */
public class FilterPage extends AppCompatActivity{
    ArrayList<Filter> filters;
    FilterAdapter filterListAdapter;
    RecyclerView filterListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter);
        filters = new ArrayList<Filter>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ArrayList<Filter> list = bundle.getParcelableArrayList("filters");
            filters.clear();
            // Use the list as needed
            for (int i =0; i < list.size(); i++){
                filters.add(list.get(i));
            }
        }
//        filters.add(new Filter());

        filterListAdapter = new FilterAdapter(filters);
        filterListView = findViewById(R.id.filter_list);
        filterListView.setAdapter(filterListAdapter);
        filterListView.setLayoutManager(new LinearLayoutManager(this));
        filterListView.setItemAnimator(null);     // fixes bug in Android


        final FloatingActionButton addButton = findViewById(R.id.add_filter_button);
        addButton.setOnClickListener(v -> {
            filters.add(new Filter());
            filterListAdapter.notifyItemInserted(filters.size()-1);
            Log.e("Filter","adding a new item " + filterListAdapter.getItemCount());
        });

        final Button backButton = findViewById(R.id.filter_back_button);
        backButton.setOnClickListener(view -> {
            ArrayList<Filter> filterList= new ArrayList<>();
            for (int i = 0; i < filterListAdapter.getItemCount(); i++) {
                FilterAdapter.MyViewHolder holder =
                        (FilterAdapter.MyViewHolder) filterListView.findViewHolderForAdapterPosition(i);
                if (holder != null) {
                    Filter dataItem = holder.getFilter();
                    filterList.add(dataItem);

                }
            }
            Bundle bundle_send = new Bundle();
            bundle_send.putParcelableArrayList("filters", filterList);
            Intent i = new Intent(FilterPage.this, MainActivity.class);
            i.putExtras(bundle_send);
            startActivity(i);
        });
    }
}
