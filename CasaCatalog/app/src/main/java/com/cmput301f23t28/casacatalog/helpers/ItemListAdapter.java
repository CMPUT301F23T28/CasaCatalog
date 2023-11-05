package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.Item;

public class ItemListAdapter extends ArrayAdapter<Item> {
    private final Context context;
    private ArrayList<Item> itemList;

    public ItemListAdapter(Context context, ArrayList<Item> itemList) {
        super(context, 0, itemList);
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.content, parent, false);
        }

        Item item = itemList.get(position);
        TextView itemName = view.findViewById(R.id.item_name);
        TextView dateName = view.findViewById(R.id.date_name);
        TextView amountName = view.findViewById(R.id.amount_name);
        TextView tagName = view.findViewById(R.id.tag_name);


        itemName.setText(item.getName());
//        dateName.setText(item.getDate().toString());
        amountName.setText(item.getPrice().toString());
        tagName.setText(item.getTags());

        return view;
    }
}
