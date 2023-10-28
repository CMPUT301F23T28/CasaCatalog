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

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference itemList;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> itemAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseFirestore.getInstance();

        itemList = db.collection("items");

        dataList = new ArrayList<>();  // Other code omitted }
        String test = "Test";
        dataList.add(test);
//        itemAdapter.notifyDataSetChanged();


    }

}