package com.cmput301f23t28.casacatalog.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.TagsListAdapter;
import com.cmput301f23t28.casacatalog.helpers.ToolbarBuilder;
import com.cmput301f23t28.casacatalog.models.Tag;

import java.util.ArrayList;

/**
 * Activity for editing tags associated with an item.
 * Allows users to add new tags and modify existing ones.
 */
public class EditTagsActivity extends AppCompatActivity {

    /**
     * Initializes the activity, sets up the RecyclerView for tags, and handles creation and deletion of tags.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise it is null.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tags);
        ToolbarBuilder.create(this, getString(R.string.title_edit_tags));

        // Get passed in item from intent, if there is one
        ArrayList<Tag> tags = getIntent().getParcelableArrayListExtra("tags");

        TagsListAdapter tagAdapter = new TagsListAdapter(this, tags);
        RecyclerView tagsListView = findViewById(R.id.tags_list);
        tagsListView.setAdapter(tagAdapter);
        tagsListView.setLayoutManager(new LinearLayoutManager(this));
        tagAdapter.notifyDataSetChanged();

        // Create new tag when add button is pressed
        findViewById(R.id.createTagButton).setOnClickListener(view -> {
            EditText nameInput = findViewById(R.id.newTagName);
            Database.tags.createTag(nameInput.getText().toString());
            tagAdapter.notifyDataSetChanged();
        });

        // Capture all selected tags from list and add to item's tag list
        // when the back button is pressed
        findViewById(R.id.backButtonFromEditTags).setOnClickListener(view -> {
            // Delete any tags with no uses
            for (Tag tag : Database.tags.getTags()) {
                if (tag.getUses() <= 0) Database.tags.deleteTag(tag.getName());
            }

            // Send new tags copy back
            Intent ret = new Intent();
            ret.putExtra("tags", tags);
            setResult(Activity.RESULT_OK, ret);

            finish();
        });
    }
}
