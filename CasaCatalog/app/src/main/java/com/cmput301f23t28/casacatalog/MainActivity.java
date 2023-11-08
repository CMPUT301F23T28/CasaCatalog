package com.cmput301f23t28.casacatalog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.cmput301f23t28.casacatalog.models.ItemHandler;
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
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ListView itemListView;

    private ItemHandler itemHandler;
    private ArrayAdapter<Item> itemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemHandler = new ItemHandler();

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
            itemHandler.addItem(item);
        }
        String itemS =  "TV";
        Double amount = 50.0;
        Item itemTest = new Item();
        itemTest.setName(itemS);
        itemTest.setPrice(amount);
        itemHandler.addItem(itemTest);

//        Test delete method (won't work due to delay?)
//        Log.e("TEst id", itemTest.getId());
//        itemHandler.deleteItem(itemTest);



        itemAdapter = new ItemListAdapter(this, itemHandler.getItemList());
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);

        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });
    }
}
