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

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        ItemName = itemView.findViewById(R.id.ItemName);
        ItemPurchaseDate = itemView.findViewById(R.id.ItemPurchaseDate);
        ItemPrice = itemView.findViewById(R.id.ItemPrice);
        ItemTags = itemView.findViewById(R.id.ItemTags);
    }

    public void setItemName(String itemName) {
        if (ItemName != null){
            ItemName.setText(itemName);
        }
    }

    public void setItemPurchaseDate(String itemPurchaseDate) {
        if (ItemPurchaseDate != null){
            ItemPurchaseDate.setText(itemPurchaseDate);
        }

    }

    public void setItemPrice(String itemPrice) {
        if (ItemPrice != null){
            ItemPrice.setText(itemPrice);
        }

    }

    public void setItemTags(ArrayList<Chip> chips) {
        if( chips != null ){
            for(Chip c : chips) ItemTags.addView(c);
        }
    }
}
