package com.cmput301f23t28.casacatalog.views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.helpers.TagsListAdapter;
import com.cmput301f23t28.casacatalog.models.Item;
import com.cmput301f23t28.casacatalog.models.Tag;

import java.util.ArrayList;

public class EditTagsActivity extends AppCompatActivity {

    private Item item = null;
    private ArrayList<Tag> tagList;
    private RecyclerView tagsListView;
    private TagsListAdapter tagAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tags);

        // Get passed in item from intent, if there is one
        this.item = (Item) getIntent().getSerializableExtra("item");
        this.tagList = Database.tags.getTags();

        ArrayList<Tag> newTags = new ArrayList<>();
        tagAdapter = new TagsListAdapter(this, newTags);
        tagsListView = findViewById(R.id.tags_list);
        tagsListView.setAdapter(tagAdapter);
        tagsListView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: BLOCKED! NEED EDIT ITEM ACTIVITY DONE TO PROPERLY IMPLEMENT
        // Read item for already selected tags
        // set state of checkboxes accordingly
        // TODO: set newTags to item's existing tags
        /*
        if( this.item != null ){
            for(Tag tag : this.item.getTags()){
                for(int i = 0; i < this.tagList.size(); i++){
                    TextView text = tagsListView.getChildAt(i).findViewById(R.id.tagName);
                    if(tag.getName().contentEquals(text.getText())){
                        CheckBox c = tagsListView.getChildAt(i).findViewById(R.id.tagCheckBox);
                        c.setActivated(true);
                    }
                }
            }
        }
         */

        // Create new tag when add button is pressed
        findViewById(R.id.createTagButton).setOnClickListener(view -> {
            EditText nameInput = findViewById(R.id.newTagName);
            Database.tags.createTag(nameInput.getText().toString());
            tagAdapter.notifyDataSetChanged();
        });

        // Prepare list of new tags for item
        //((RecyclerView)findViewById(R.id.tags_list)).setOn

        // Capture all selected tags from list and add to item's tag list
        // when the back button is pressed
        findViewById(R.id.backButtonTempName).setOnClickListener(view -> {

            // Add new tags to item, if we are editing one
            if(item != null) {
                for (Tag tag : newTags) {
                    item.addTag(tag);
                    tag.setUses(tag.getUses() + 1); // TODO: decrease uses when tag is removed
                }
            }

            // Delete any tags with no uses
            /*
            for(Tag tag : MainActivity.tagDatabase.getTags()){
                if(tag.getUses() <= 0) MainActivity.tagDatabase.deleteTag(tag.getName());
            }
             */

            finish();
        });
    }
}
