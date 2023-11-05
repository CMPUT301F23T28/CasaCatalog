package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.models.User;

public class UserActivity extends AppCompatActivity {

    private ImageView userProfileImage;
    private TextView usernameTextView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userProfileImage = findViewById(R.id.userProfileImage);
        usernameTextView = findViewById(R.id.usernameTextView);


        user = new User("Kai"); // Sample username
        updateUI();

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // This will close the current activity and return to the previous one
            }
        });
    }

    private void updateUI() {
        usernameTextView.setText(user.getName());
        // Set the profile image, if you have different images for different users
        userProfileImage.setImageResource(R.drawable.profile);
    }
}
