package com.cmput301f23t28.casacatalog.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.database.TagDatabase;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.cmput301f23t28.casacatalog.helpers.ItemListAdapter;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.ItemHandler;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.cmput301f23t28.casacatalog.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> itemList;
    private RecyclerView itemListView;
    private TextView totalValueTextView;

    private ItemHandler itemHandler;
    private ItemListAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Database.initialize();

        // If first time using app, start create user activity
        String deviceId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Database.users.getCollection().document(deviceId).get().addOnCompleteListener(task -> {
            if( !task.isSuccessful() || !task.getResult().exists() ){
                startActivity(new Intent(this, NewUserActivity.class));
            }
        });

        itemHandler = new ItemHandler();

//        Test delete method (won't work due to delay?)
//        Log.e("TEst id", itemTest.getId());
//        itemHandler.deleteItem(itemTest);



        itemAdapter = new ItemListAdapter(this, itemHandler.getItemList(), itemHandler.getDb().getItemsRef());
        itemListView = findViewById(R.id.items_list);
        itemListView.setAdapter(itemAdapter);


        itemListView.setLayoutManager(new LinearLayoutManager(this));

        totalValueTextView = findViewById(R.id.InventoryValueNumber); // Initialize the TextView
        itemHandler.getDb().getItemsRef().addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Log.e("Firestore", error.toString());
                    return;
                }
                if (value != null){
                    itemHandler.getItemList().clear();
                    for (QueryDocumentSnapshot doc : value) {
                        String itemID = doc.getId();
                        Log.d("ITEM ID QUERY", itemID);
                        String itemname = doc.getString("name");
                        Double pricename = doc.getDouble("price");
                        String dateinstring = doc.getString("date");

                        String itemMake = doc.getString("make");
                        String itemModel = doc.getString("model");
                        String itemDescription = doc.getString("description");
                        String itemComment = doc.getString("comment");
                        String itemSerialNumber = doc.getString("serialNumber");

                        Log.i("Firestore", String.format("Item(%s,%s) fetched", itemname,
                                pricename));
                        Item addItem = new Item();
                        addItem.setId(itemID);
                        addItem.setName(itemname);
                        addItem.setPrice(pricename);

                        //add date to the addItem
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.ENGLISH);
                        if (dateinstring != null) {
                            try {
                                Date date = sdf.parse(dateinstring);
                                addItem.setDate(date);
                            } catch (ParseException e) {
                                Log.e("ParseException", "ParseException" + e.toString());
                            }
                        }

                        addItem.setMake(itemMake);
                        addItem.setModel(itemModel);
                        addItem.setDescription(itemDescription);
                        addItem.setComment(itemComment);
                        addItem.setSerialNumber(itemSerialNumber);

                        itemHandler.getItemList().add(addItem);
                        itemAdapter.notifyDataSetChanged();

                        // After adding all items to the itemList, calculate the total value
                        double totalValue = 0;
                        for (Item item : itemHandler.getItemList()) {
                            if (item.getPrice() != null) {
                                totalValue += item.getPrice();
                            }
                        }

                        // Update the TextView with the total value
                        String totalValueFormatted = String.format(Locale.ENGLISH, "$%.2f", totalValue);
                        totalValueTextView.setText(totalValueFormatted);

                    }
                }
            }
        });


        final FloatingActionButton addButton = findViewById(R.id.add_item_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Sends the user to the 'add item' activity, allowing them to input their item
                 * and all of its relevant details.
                 */

                Intent addItemActivityIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(addItemActivityIntent);

            }
        });

        // Find the userProfileImage view by its ID and set an OnClickListener
        CircleImageView userProfileImage = findViewById(R.id.userProfileImage);
        userProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UserActivity
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

    }
}
