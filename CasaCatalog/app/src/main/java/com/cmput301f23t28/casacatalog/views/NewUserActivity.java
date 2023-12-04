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
import com.cmput301f23t28.casacatalog.helpers.NonEmptyInputWatcher;

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

        registerInputValidators();

        findViewById(R.id.addUserButton).setOnClickListener(view -> {
            String name = ((EditText)findViewById(R.id.editNewName)).getText().toString();
            Database.users.createUser(name);
            finish();
        });
    }

    /**
     * Registers input listeners for relevant inputs to ensure their validity.
     */
    private void registerInputValidators(){
        EditText input = findViewById(R.id.editNewName);
        Button button = findViewById(R.id.addUserButton);
        input.addTextChangedListener(new NonEmptyInputWatcher(input, button) {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);

                // Unique username
                if( UserDatabase.isNameUnique(s.toString()) ){
                    button.setEnabled(true);
                }else{
                    input.setError("Username is taken!");
                    button.setEnabled(false);
                }
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });
    }
}
