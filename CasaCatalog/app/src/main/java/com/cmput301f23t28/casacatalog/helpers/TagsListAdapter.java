package com.cmput301f23t28.casacatalog.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
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

        // Update newTags based on selection made to list checkboxes
        // TODO: theres has to be a better way to do this
        view.setOnClickListener(l -> {
            // Get tag object that list item refers to
            Tag tag = null;
            TextView name = l.findViewById(R.id.tagName);
            for(Tag t : MainActivity.tagDatabase.getTags()){
                if(t.getName().equals(name.toString())) tag = t;
            }

            // Update newTags list based on checkbox state
            CheckBox c = l.findViewById(R.id.tagCheckBox);
            if( !c.isChecked() ){
                if(!newTags.contains(tag)) newTags.add(tag);
                c.setChecked(true);
            }else{
                newTags.remove(tag);
                c.setChecked(false);
            }
        });

        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag tag = MainActivity.tagDatabase.getTags().get(position);
        holder.setTagName(tag.getName());
    }

    @Override
    public int getItemCount() {
        return MainActivity.tagDatabase.getTags().size();
    }

}
