package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
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
public class ItemListAdapter extends RecyclerView.Adapter<ItemHolder> implements ListClickListener<ItemHolder> {
    private final Context context;
    private final ArrayList<Item> itemList;

    private final ActivityResultLauncher<Intent> editItemLauncher;

    private final VisibilityCallback mVisibilityCallback;
    private boolean isEditingState = false;

    /**
     * Construct for an ItemListAdapter. Has access to the context, data and db reference.
     * @param context Any context in the application.
     * @param itemList The list of items.
     * @param visibilityCallback Callback interface for handling visibility changes in the activity.
     */
    public ItemListAdapter(Context context, ArrayList<Item> itemList, ActivityResultLauncher<Intent> editItemLauncher, VisibilityCallback visibilityCallback) {
        super();
        this.context = context;
        this.itemList = itemList;
        this.mVisibilityCallback = visibilityCallback;
        this.editItemLauncher = editItemLauncher;
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
            holder.setItemTags(item.getTagsAsChips(context));
        }

        if (item.getPhotosURL().size() > 0) {
            // Just sets to first image in list.
            holder.setItemImage(item.getPhotosURL().get(0));
        }
        else {

        }
    }

    /**
     * Sends the user to the 'edit item' activity, allowing them to edit their current
     * item and all of its relevant details.
     * Also intercepts and handles single clicks for editing and deleting.
     */
    @Override
    public void onItemClick(int position, ItemHolder holder) {
        Item item = itemList.get(position);

        // behaviour if following a long click event.
        if (isEditingState) {
            item.toggleSelected();
            notifyDataSetChanged();
            return;
        }

        Intent i = new Intent(context, EditItemActivity.class);
        i.putExtra("item", item);
        this.editItemLauncher.launch(i);
    }

    /**
     * initiates process for deleting and editing items from main screen
     * @param position the index of the data to affect
     * @param holder the ItemHolder being affected
     */
    @Override
    public void onItemLongClick(int position, ItemHolder holder) {
        isEditingState = !isEditingState;
        if (isEditingState) {
            if (mVisibilityCallback != null) {
                Item item = itemList.get(position);
                item.setSelected(true);
                mVisibilityCallback.toggleVisibility();
            }
        } else {
            // remove all selections
            int totalItems = itemList.size();
            for (int i = 0; i < totalItems; i++) {
                Item item = itemList.get(i);
                item.setSelected(false);
            }
            mVisibilityCallback.toggleVisibility();
        }
        notifyDataSetChanged();

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
