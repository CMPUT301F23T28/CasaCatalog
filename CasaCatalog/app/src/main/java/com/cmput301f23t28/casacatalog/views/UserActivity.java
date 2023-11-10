package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.User;

public class UserActivity extends AppCompatActivity {

    private ImageView userProfileImage;
    private TextView usernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userProfileImage = findViewById(R.id.userProfileImage);
        usernameTextView = findViewById(R.id.usernameTextView);

        usernameTextView.setText(Database.users.getCurrentUser().getName());
        // Set the profile image, if you have different images for different users
        userProfileImage.setImageResource(R.drawable.profile);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
    }
}
