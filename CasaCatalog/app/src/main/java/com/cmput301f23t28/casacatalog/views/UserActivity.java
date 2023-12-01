package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.UserDatabase;
import com.cmput301f23t28.casacatalog.helpers.ToolbarBuilder;

/**
 * Represents an activity for user interactions within the app.
 * This activity is responsible for displaying the user's profile image
 * and username, as well as handling the back button functionality.
 */
public class UserActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting.
     * This is where most initialization should go: calling setContentView(int)
     * to inflate the activity's UI, using findViewById(int) to programmatically
     * interact with widgets in the UI, calling setOnClickListener(View.OnClickListener)
     * to add handlers for click events, etc.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ToolbarBuilder.create(this, getString(R.string.title_profile));

        // Hydrate views
        ((TextView) findViewById(R.id.userNameTextView)).setText(UserDatabase.getUserName());
        ((TextView) findViewById(R.id.userCreatedTextView)).setText(getString(R.string.user_profile_created_text, UserDatabase.getCreated()));

        // Set the profile image, if you have different images for different users
        ((ImageView) findViewById(R.id.userProfileImage)).setImageResource(R.drawable.profile);
    }
}
