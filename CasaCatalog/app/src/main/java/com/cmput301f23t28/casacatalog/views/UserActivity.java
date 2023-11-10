package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;

/**
 * Represents an activity for user interactions within the app.
 * This activity is responsible for displaying the user's profile image
 * and username, as well as handling the back button functionality.
 */
public class UserActivity extends AppCompatActivity {

    private ImageView userProfileImage;
    private TextView usernameTextView;

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

        userProfileImage = findViewById(R.id.userProfileImage);
        usernameTextView = findViewById(R.id.usernameTextView);

        if(Database.users != null && Database.users.getCurrentUser(this) != null && Database.users.getCurrentUser(this).getName() != null){
            usernameTextView.setText(Database.users.getCurrentUser(this).getName());
        }
        // Set the profile image, if you have different images for different users
        userProfileImage.setImageResource(R.drawable.profile);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
    }
}
