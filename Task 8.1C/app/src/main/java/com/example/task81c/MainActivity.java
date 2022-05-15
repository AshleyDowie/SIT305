package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.task81c.data.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText userNameEditText;
    EditText passwordEditText;
    Button loginButton;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        userNameEditText = findViewById(R.id.userNameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpButton = findViewById(R.id.signUpButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(userName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password == null || password.trim().length() == 0)
                {
                    Toast.makeText(MainActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                AttemptLogin(userName, password);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSignUpActivity();
            }
        });
    }

    public void AttemptLogin(String userName, String password)
    {
        Integer userId = dbHelper.GetUser(userName, password);
        if(userId == null)
        {
            Toast.makeText(MainActivity.this, "Username or password is invalid.", Toast.LENGTH_SHORT).show();
            return;
        }
        OpenHomeActivity(userId);
    }

    public void OpenHomeActivity(int userId)
    {
        Intent loginIntent = new Intent(MainActivity.this, HomePageActivity.class);
        loginIntent.putExtra("userId", userId);
        startActivity(loginIntent);
        finish();
    }

    public void OpenSignUpActivity()
    {
        Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
    }
}