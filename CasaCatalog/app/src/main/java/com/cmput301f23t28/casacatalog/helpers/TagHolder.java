package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.google.android.material.chip.ChipGroup;

public class TagHolder extends RecyclerView.ViewHolder {
    private TextView tagName;

    public TagHolder(@NonNull View itemView) {
        super(itemView);
        tagName = itemView.findViewById(R.id.tagName);
    }

    public void setTagName(String newName) {
        if (tagName != null){
            tagName.setText(newName);
        }
    }

}
