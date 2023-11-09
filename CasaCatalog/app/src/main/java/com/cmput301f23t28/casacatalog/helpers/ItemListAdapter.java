package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.views.AddItemActivity;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.cmput301f23t28.casacatalog.views.MainActivity;
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
        /**
         * Goes to 'edit item' page when an item is clicked.
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Sends the user to the 'edit item' activity, allowing them to edit their current
                 * item and all of its relevant details.
                 */

                // NOTE: Not a great way to do this (clicking item). Apparently there's a much better
                // way so I can implement this inside of the activity but I'm in a rush.

                Intent editItemActivityIntent = new Intent(context, EditItemActivity.class);
                // Tests (is null at the moment?? no idea why??)
                if (item.getId() != null) {
                    Log.d("ITEM ID ADAPTER", item.getId());
                }
                else {
                    Log.d("ITEM ID ADAPTER", "NULL");
                }
                editItemActivityIntent.putExtra("ITEM_ID", item.getId());
                editItemActivityIntent.putExtra("ITEM_POSITION", position);
                editItemActivityIntent.putExtra("ITEM_NAME", item.getName());
                editItemActivityIntent.putExtra("ITEM_PRICE", item.getPrice());
                if (item.getDate() != null) {
                    editItemActivityIntent.putExtra("ITEM_DATE", item.getDate().toString());
                }
                editItemActivityIntent.putExtra("ITEM_TAGS", item.getTags());
                context.startActivity(editItemActivityIntent);
            }
        });
        Log.e("Shown", "Item" + item.getName());
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
