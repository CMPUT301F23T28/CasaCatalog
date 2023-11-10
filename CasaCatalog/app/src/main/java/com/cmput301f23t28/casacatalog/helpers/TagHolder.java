package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.cmput301f23t28.casacatalog.R;

/**
 * ViewHolder for a tag item in a RecyclerView, used in TagsListAdapter.
 * It holds the view for an individual tag and its associated data.
 */
public class TagHolder extends RecyclerView.ViewHolder {
    private TextView tagName;

    /**
     * Constructs a TagHolder with the specified itemView.
     *
     * @param itemView The view of the RecyclerView item.
     */
    public TagHolder(@NonNull View itemView) {
        super(itemView);
        tagName = itemView.findViewById(R.id.tagName);
    }

    /**
     * Sets the name of the tag to the TextView.
     *
     * @param newName The new name to be set for the tag.
     */
    public void setTagName(String newName) {
        if (tagName != null) {
            tagName.setText(newName);
        }
    }
}
