package com.cmput301f23t28.casacatalog.helpers;

public interface ItemListClickListener {
    void onItemClick(int position, ItemHolder holder);
    void onItemLongClick(int position, ItemHolder holder);
}
