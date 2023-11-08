package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
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

        holder.setItemName(item.getName());
        if (item.getPrice() != null){
            holder.setItemPrice(item.getPrice().toString());
        }
//        holder.setItemPurchaseDate(item.getDate().toString());
        /// TODO:
        /// Need to implement setting the tags. Probably should receive some kind of collection of tags
        /// as setItemTags and then that method can attach them somehow to the ChipGroup in the ItemHolder
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}
