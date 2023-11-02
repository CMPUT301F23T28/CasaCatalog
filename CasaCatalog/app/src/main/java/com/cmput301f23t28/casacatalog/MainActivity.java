package com.cmput301f23t28.casacatalog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.models.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> itemList;
    private ListView itemListView;

    private ArrayAdapter<Item> itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemList = new ArrayList<Item>();
        String[] items = {
                "Chair", "Table"
        };

        Double[] amounts = {
                22.0, 40.0
        };

        for (int i = 0; i < items.length; i++) {
            Item item = new Item();
            item.setName(items[i]);
            item.setPrice(amounts[i]);
            itemList.add(item);
        }
        itemAdapter = new ItemListAdapter(this, itemList);
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);

//        final FloatingActionButton addButton = findViewById(R.id.add_city_button);
//        addButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
//            }
//        });
    }
}
