package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cmput301f23t28.casacatalog.R;
import com.google.android.material.chip.ChipGroup;

public class ItemHolder extends RecyclerView.ViewHolder{
    private TextView ItemName;
    private TextView ItemPurchaseDate;
    private TextView ItemPrice;
    private ChipGroup ItemTags;

    public ItemHolder(@NonNull View itemView) {
        super(itemView);
        ItemName = itemView.findViewById(R.id.ItemPrice);
        ItemPurchaseDate = itemView.findViewById(R.id.ItemPurchaseDate);
        ItemPrice = itemView.findViewById(R.id.ItemPrice);
        ItemTags = itemView.findViewById(R.id.ItemTags);
    }

    public void setItemName(String itemName) {
        ItemName.setText(itemName);
    }

    public void setItemPurchaseDate(String itemPurchaseDate) {
        ItemPurchaseDate.setText(itemPurchaseDate);
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice.setText(itemPrice);
    }

    public void setItemTags(ChipGroup itemTags) {
        ItemTags = itemTags;
    }
}
