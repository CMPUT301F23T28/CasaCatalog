package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.CollectionReference;

public class ItemListAdapter extends RecyclerView.Adapter<ItemHolder> {
    private final Context context;
    private ArrayList<Item> itemList;

    private CollectionReference itemsRef;

    public ItemListAdapter(Context context, ArrayList<Item> itemList, CollectionReference itemsRef) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.itemsRef = itemsRef;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
//        Log.e();
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = itemList.get(position);


        if (item.getName() != null) {
            holder.setItemName(item.getName());
        }

        if (item.getPrice() != null){
            holder.setItemPrice(item.getPrice().toString());
        }
        if (item.getDate() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            holder.setItemPurchaseDate(sdf.format(item.getDate()));
        }
        Log.e("Shown", "Item" + item.getName());
//        holder.setItemPurchaseDate(item.getDate().toString());

        if( item.getTags() != null ){
            ArrayList<Chip> chips = new ArrayList<>();

            // Create chips for each tag, add to chip group
            for(Tag tag : item.getTags()) {
                Chip c = new Chip(context);
                c.setText(tag.getName());
                chips.add(c);
            }

            holder.setItemTags(chips);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
