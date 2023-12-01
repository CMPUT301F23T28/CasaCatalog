package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;

/**
 * ViewHolder for a tag item in a RecyclerView, used in TagsListAdapter.
 * It holds the view for an individual tag and its associated data.
 */
public class TagHolder extends RecyclerView.ViewHolder {
    private final View view;

    /**
     * Constructs a TagHolder with the specified itemView.
     *
     * @param itemView The view of the RecyclerView item.
     */
    public TagHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
    }

    /**
     * Sets the name of the tag to the TextView.
     * @param name The new name to be set for the tag.
     */
    public void setTagName(String name) {
        ((TextView) view.findViewById(R.id.tagName)).setText(name);
    }

    /**
     * Sets the uses of the tag to the TextView.
     * @param uses The new uses to be set for the tag.
     */
    public void setUsesText(int uses) {
        // minor TODO: use string resource
        ((TextView) view.findViewById(R.id.tagUsages)).setText(uses + " uses");
    }

    /**
     * Sets the checkbox of the tag to the TextView.
     * @param checked The new checkbox state.
     */
    public void setChecked(boolean checked) {
        ((CheckBox) view.findViewById(R.id.tagCheckBox)).setChecked(checked);
    }
}
