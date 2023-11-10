package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;

/**
 * The new user activity that starts when the application is launched for the first time.
 * It prompts the user to create a username to identify themselves in the database.
 */
public class NewUserActivity extends AppCompatActivity {
    // TODO: could add way to set profile picture in this activity, not really necessary
    /**
     * Initializes the activity, registers listeners to allow inputting a username.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        findViewById(R.id.addUserButton).setOnClickListener(view -> {
            EditText nameInput = findViewById(R.id.editNewName);

            // TODO: validate input
            Database.users.createUser(this, nameInput.getText().toString());
            finish();
        });
    }
}
