package com.cmput301f23t28.casacatalog.helpers;

import androidx.recyclerview.widget.RecyclerView;

public interface ListClickListener<T extends RecyclerView.ViewHolder> {
    void onItemClick(int position, T holder);
    void onItemLongClick(int position, T holder);
}
