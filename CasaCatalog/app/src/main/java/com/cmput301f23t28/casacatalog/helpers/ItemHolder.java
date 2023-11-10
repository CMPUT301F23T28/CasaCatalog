package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class ItemHolder extends RecyclerView.ViewHolder{
    private TextView ItemName;
    private TextView ItemPurchaseDate;
    private TextView ItemPrice;
    private ChipGroup ItemTags;

    /**
     * Constructor for ItemHolder. Basically connects the UI elements to an in code reference.
     * @param itemView
     */
    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        ItemName = itemView.findViewById(R.id.ItemName);
        ItemPurchaseDate = itemView.findViewById(R.id.ItemPurchaseDate);
        ItemPrice = itemView.findViewById(R.id.ItemPrice);
        ItemTags = itemView.findViewById(R.id.ItemTags);
    }

    /**
     * Sets the UI Item Component in the item row.
     * @param itemName
     */
    public void setItemName(String itemName) {
        if (ItemName != null){
            ItemName.setText(itemName);
        }
    }

    /**
     * Sets the UI Item Purchase Date in the item row.
     * @param itemPurchaseDate
     */
    public void setItemPurchaseDate(String itemPurchaseDate) {
        if (ItemPurchaseDate != null){
            ItemPurchaseDate.setText(itemPurchaseDate);
        }

    }

    /**
     * Set the UI Item Price in the item row.
     * @param itemPrice
     */
    public void setItemPrice(String itemPrice) {
        if (ItemPrice != null){
            ItemPrice.setText(itemPrice);
        }

    }

     /**
     * Set the UI Tags in the item row.
     * @param chips
     */
    public void setItemTags(ArrayList<Chip> chips) {
        if( chips != null ){
            for(Chip c : chips) ItemTags.addView(c);
        }
    }
}
