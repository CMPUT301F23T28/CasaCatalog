package com.cmput301f23t28.casacatalog.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cmput301f23t28.casacatalog.R;
import com.cmput301f23t28.casacatalog.database.Database;
import com.cmput301f23t28.casacatalog.models.User;

// TODO: could add way to set profile picture in this activity, not really necessary
public class NewUserActivity extends AppCompatActivity {
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
