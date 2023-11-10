package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.User;

/**
 * Represents an activity for user interactions within the app.
 * This activity is responsible for displaying the user's profile image
 * and username, as well as handling the back button functionality.
 */
public class UserActivity extends AppCompatActivity {

    private ImageView userProfileImage; // View for user's profile image
    private TextView usernameTextView; // View for user's name
    private User user; // User model for this activity

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

        // Initialize a User object with sample data for display purposes
        user = new User("Kai");
        // Update the UI with the User data
        updateUI();

        // Set up the back button to finish the activity and return to the previous one
        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // This will close the current activity and return to the previous one
            }
        });
    }

    /**
     * Updates the UI elements with the user's data.
     * Sets the username TextView with the user's name, and sets
     * the userProfileImage ImageView with the user's profile image.
     */
    private void updateUI() {
        usernameTextView.setText(user.getName());
        // Set the profile image from resources
        userProfileImage.setImageResource(R.drawable.profile);
    }
}
