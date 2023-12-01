package com.cmput301f23t28.casacatalog.helpers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cmput301f23t28.casacatalog.R;

public class ToolbarBuilder {
    public static void create(AppCompatActivity activity, String title) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);

        if(title == null) title = activity.getString(R.string.app_name);
        toolbar.setTitle(title);

        activity.setSupportActionBar(toolbar);

        // Back button functionality
        toolbar.setNavigationOnClickListener(view -> activity.finish());
    }
}
