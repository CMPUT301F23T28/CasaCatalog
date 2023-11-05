package com.cmput301f23t28.casacatalog.controllers;

import android.net.wifi.p2p.WifiP2pManager;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.google.android.material.chip.ChipGroup;

import java.util.Date;

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
}
