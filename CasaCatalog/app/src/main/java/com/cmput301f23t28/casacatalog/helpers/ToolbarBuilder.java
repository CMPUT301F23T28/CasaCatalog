package com.cmput301f23t28.casacatalog.helpers;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cmput301f23t28.casacatalog.R;

public class ToolbarBuilder {

    /**
     * Creates a toolbar with a back button that returns to the previous activity.
     * @param activity Activity that the toolbar is for
     * @param title Title text for the toolbar
     */
    public static void create(AppCompatActivity activity, String title) {
        create(activity, title, view -> activity.finish());
    }

    /**
     * Creates a toolbar with a configurable back button.
     * @param activity Activity that the toolbar is for
     * @param title Title text for the toolbar
     */
    public static void create(AppCompatActivity activity, String title, View.OnClickListener view) {
        Toolbar toolbar = activity.findViewById(R.id.toolbar);

        if(title == null) title = activity.getString(R.string.app_name);
        toolbar.setTitle(title);

        activity.setSupportActionBar(toolbar);

        // Back button functionality
        toolbar.setNavigationOnClickListener(view);
    }
}
