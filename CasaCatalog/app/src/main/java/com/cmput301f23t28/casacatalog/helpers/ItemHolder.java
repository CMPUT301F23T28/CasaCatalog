package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.views.EditItemActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

/**
 * A RecyclerView ViewHolder that stores all data related to rendering an item in the ItemList
 */
public class ItemHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
    private TextView ItemName;
    private TextView ItemPurchaseDate;
    private TextView ItemPrice;
    private ChipGroup ItemTags;
    private ItemListClickListener mListener;
    private ConstraintLayout ItemContainer;

    /**
     * Constructor for ItemHolder. Basically connects the UI elements to an in code reference.
     * @param itemView The item view.
     */
    public ItemHolder(@NonNull View itemView, ItemListClickListener listener) {
        super(itemView);

        this.mListener = listener;

        ItemContainer = itemView.findViewById(R.id.ItemContainer);
        ItemName = itemView.findViewById(R.id.ItemName);
        ItemPurchaseDate = itemView.findViewById(R.id.ItemPurchaseDate);
        ItemPrice = itemView.findViewById(R.id.ItemPrice);
        ItemTags = itemView.findViewById(R.id.ItemTags);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    /**
     * Sets the UI Item Component in the item row.
     * @param itemName The name of the item.
     */
    public void setItemName(String itemName) {
        if (ItemName != null) {
            ItemName.setText(itemName);
        }
    }

    /**
     * Sets the UI Item Purchase Date in the item row.
     * @param itemPurchaseDate The purchase date of the item.
     */
    public void setItemPurchaseDate(String itemPurchaseDate) {
        if (ItemPurchaseDate != null) {
            ItemPurchaseDate.setText(itemPurchaseDate);
        }
    }

    /**
     * Sets the background color of the main container in the ViewHolder.
     * @param color the color!
     */
    public void setSelectedStyle(int color) {
        ItemContainer.setBackgroundColor(color);
    }
    /**
     * Set the UI Item Price in the item row.
     * @param itemPrice The price of the item.
     */
    public void setItemPrice(String itemPrice) {
        if (ItemPrice != null){
            ItemPrice.setText(itemPrice);
        }

    }

     /**
     * Set the UI Tags in the item row.
     * @param chips An array of chips to add to the ChipGroup.
     */
    public void setItemTags(ArrayList<Chip> chips) {
        if( chips != null ){
            ItemTags.removeAllViews();
            for(Chip c : chips) ItemTags.addView(c);
        }
    }

    /**
     * Called when a long click is detected on the ViewHolder's view.
     * Notifies the registered listener about the long click event.
     * @param v The view that was clicked.
     * @return True if the listener consumes the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        if (mListener != null) {
            mListener.onItemLongClick(getAdapterPosition(), this);
            return true;
        }
        return false;
    }

    /**
     * Called when a click is detected on the ViewHolder's view.
     * Notifies the registered listener about the click event.
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onItemClick(getAdapterPosition(), this);
        }
    }
}
