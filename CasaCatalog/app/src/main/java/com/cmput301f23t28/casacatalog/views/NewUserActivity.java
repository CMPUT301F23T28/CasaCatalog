package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.database.UserDatabase;

/**
 * The new user activity that starts when the application is launched for the first time.
 * It prompts the user to create a username to identify themselves in the database.
 */
public class NewUserActivity extends AppCompatActivity {
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

        EditText input = findViewById(R.id.editNewName);
        Button button = findViewById(R.id.addUserButton);

        // Input validation
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                button.setEnabled(false);

                // Non-empty input
                if( s.toString().isEmpty() ){
                    input.setError("Please decide a username.");
                    return;
                }

                // Unique username
                if( UserDatabase.isNameUnique(s.toString()) ){
                    button.setEnabled(true);
                }else{
                    input.setError("Username is taken!");
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        findViewById(R.id.addUserButton).setOnClickListener(view -> {
            String name = input.getText().toString();

            Database.users.createUser(name);
            finish();
        });
    }
}
