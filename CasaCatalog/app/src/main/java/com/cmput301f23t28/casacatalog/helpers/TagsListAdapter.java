package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;
import com.cmput301f23t28.casacatalog.views.MainActivity;
import com.google.firebase.firestore.CollectionReference;

import java.util.ArrayList;

public class TagsListAdapter extends RecyclerView.Adapter<TagHolder> {
    private final Context context;
    private ArrayList<Tag> newTags;

    public TagsListAdapter(Context context, ArrayList<Tag> newTags) {
        this.context = context;
        this.newTags = newTags;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_row, parent, false);

        // Check checkboxes corresponding to tags already in newTags
        if( this.newTags.contains(Database.tags.findTagByName(((TextView)view.findViewById(R.id.tagName)).getText().toString()) )){
            ((CheckBox)view.findViewById(R.id.tagCheckBox)).setChecked(true);
        }

        // Update newTags based on selection made to list checkboxes
        view.setOnClickListener(l -> {
            // Get tag object that list item refers to
            TextView name = l.findViewById(R.id.tagName);
            Tag tag = Database.tags.findTagByName(name.getText().toString());

            // Update newTags list based on checkbox state
            CheckBox c = l.findViewById(R.id.tagCheckBox);
            if( !c.isChecked() ){
                if(!newTags.contains(tag)){
                    tag.setUses(tag.getUses() + 1);
                    newTags.add(tag);
                }
                c.setChecked(true);
            }else{
                tag.setUses(tag.getUses() - 1);
                newTags.remove(tag);
                c.setChecked(false);
            }
        });

        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag tag = Database.tags.getTags().get(position);
        holder.setTagName(tag.getName());
    }

    @Override
    public int getItemCount() {
        return Database.tags.getTags().size();
    }

}
