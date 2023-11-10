package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Tag;

import java.util.ArrayList;

/**
 * Adapter for providing views that represent items in a data set of tags.
 */
public class TagsListAdapter extends RecyclerView.Adapter<TagHolder> {
    private final Context context;
    private ArrayList<Tag> newTags;

    /**
     * Constructs a TagsListAdapter with the specified context and list of tags.
     *
     * @param context Context in which the adapter is operating.
     * @param newTags The list of new tags to be managed by the adapter.
     */
    public TagsListAdapter(Context context, ArrayList<Tag> newTags) {
        this.context = context;
        this.newTags = newTags;
    }

    /**
     * Called when RecyclerView needs a new {@link TagHolder} to represent an item.
     *
     * @param parent   The ViewGroup into which the new view will be added after it is bound to an adapter position.
     * @param viewType The view type of the new view.
     * @return A new TagHolder that holds a view for a tag.
     */
    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_row, parent, false);

        // Logic for initializing the state of the checkbox based on whether the tag is in newTags
        // and setting click listeners for handling tag selection.

        return new TagHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The TagHolder which should be updated to represent the contents of the item at the given position.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag tag = Database.tags.getTags().get(position);
        holder.setTagName(tag.getName());
    }

    /**
     * Returns the total number of tags in the data set held by the adapter.
     *
     * @return The size of the tags list.
     */
    @Override
    public int getItemCount() {
        return Database.tags.getTags().size();
    }

}
