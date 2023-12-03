package com.cmput301f23t28.casacatalog.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.Filter;
import com.cmput301f23t28.casacatalog.helpers.FilterAdapter;
import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * The main activity that starts when the application is launched.
 * It initializes the database, item handler, and sets up the main user interface,
 * including the RecyclerView for items and listeners for UI elements.
 */
public class FilterPage extends AppCompatActivity{
    ArrayList<Filter> filters;
    FilterAdapter itemAdapter;
    RecyclerView itemListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_filter);
        filters = new ArrayList<Filter>();
        filters.add(new Filter());


        itemAdapter = new FilterAdapter(filters);
        itemListView = findViewById(R.id.filter_list);
        itemListView.setAdapter(itemAdapter);
        itemListView.setLayoutManager(new LinearLayoutManager(this));
        itemListView.setItemAnimator(null);     // fixes bug in Android


        final FloatingActionButton addButton = findViewById(R.id.add_filter_button);
        addButton.setOnClickListener(v -> {
            filters.add(new Filter());
            itemAdapter.notifyDataSetChanged();

            Log.e("Filter","adding a new item " + itemAdapter.getItemCount());
        });

        final Button backButton = findViewById(R.id.filter_back_button);
        backButton.setOnClickListener(view -> startActivity(new Intent(FilterPage.this,
                MainActivity.class)));
    }
}
