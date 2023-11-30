package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

/**
 * A RecyclerView adapter linking ItemHolder's data to the ItemList
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemHolder> implements ItemListClickListener {
    private final Context context;
    private ArrayList<Item> itemList;

    private final VisibilityCallback mVisibilityCallback;
    private boolean isEditingState = false;

    /**
     * Construct for an ItemListAdapter. Has access to the context, data and db reference.
     * @param context Any context in the application.
     * @param itemList The list of items.
     * @param visibilityCallback Callback interface for handling visibility changes in the activity.
     */
    public ItemListAdapter(Context context, ArrayList<Item> itemList, VisibilityCallback visibilityCallback) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.mVisibilityCallback = visibilityCallback;
    }

    /**
     * Inflates each item row
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ItemHolder.
     */
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
//        Log.e();
        return new ItemHolder(view, this);
    }

    /**
     * Sets the values to each view component in the ItemRow.
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = itemList.get(position);
        // set  colors of holders based on their selection.
        if (item.getSelected()) {
            holder.setSelectedStyle(Color.LTGRAY);
        } else {
            holder.setSelectedStyle(Color.WHITE);
        }

        if (item.getName() != null) {
            holder.setItemName(item.getName());
        }

        if (item.getPrice() != null){
            holder.setItemPrice(item.getPrice().toString());
        }
        if (item.getDate() != null){
            holder.setItemPurchaseDate(item.getFormattedDate());
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

    /**
     * Goes to 'edit item' page when an item is clicked.
     */
    @Override
    public void onItemClick(int position, ItemHolder holder) {
        /**
         * Sends the user to the 'edit item' activity, allowing them to edit their current
         * item and all of its relevant details.
         */

        // behaviour if following a long click event.
        if (isEditingState) {
//            holder.toggleSelected();
            Item item = itemList.get(position);
            item.toggleSelected();
            notifyDataSetChanged();
            return;
        }

        // NOTE: Not a great way to do this (clicking item). Apparently there's a much better
        // way so I can implement this inside of the activity but I'm in a rush.
        Item item = itemList.get(position);
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
            editItemActivityIntent.putExtra("ITEM_DATE", item.getDate());
        }
        editItemActivityIntent.putExtra("ITEM_DESCRIPTION", item.getDescription());
        editItemActivityIntent.putExtra("ITEM_MAKE", item.getMake());
        editItemActivityIntent.putExtra("ITEM_MODEL", item.getModel());
        editItemActivityIntent.putExtra("ITEM_SERIAL_NUMBER", item.getSerialNumber());
        editItemActivityIntent.putExtra("ITEM_COMMENT", item.getComment());
        // TODO: Figure out whether we need to pass the tags here or not
        //editItemActivityIntent.putExtra("ITEM_TAGS", item.getTags());
        context.startActivity(editItemActivityIntent);
    }

    @Override
    public void onItemLongClick(int position, ItemHolder holder) {

        isEditingState = true;
        if (mVisibilityCallback != null) {
//            holder.toggleSelected();
            Item item = itemList.get(position);
            item.setSelected(true);
            mVisibilityCallback.toggleVisibility();
            notifyDataSetChanged();
        }
    }

    /**
     * Sets the state condition for editing
     * @param state declares if the editing state is active or not
     */
    public void setEditingState(boolean state) {
        isEditingState = state;
    }

    /**
     * Gets the number of items
     * @return Integer size of item list.
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
