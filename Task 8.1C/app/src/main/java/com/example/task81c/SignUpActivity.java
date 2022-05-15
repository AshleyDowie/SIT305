package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.User;

public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText fullNameEditText;
    EditText userNameEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    Button createAccountButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        dbHelper = new DatabaseHelper(this);
        fullNameEditText = findViewById(R.id.signUpFullNameEditText);
        userNameEditText = findViewById(R.id.signUpUserNameEditText);
        passwordEditText = findViewById(R.id.signUpPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.signUpConfirmPasswordEditText);
        createAccountButton = findViewById(R.id.signUpCreateAccountButton);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName = fullNameEditText.getText().toString();
                String userName = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if(fullName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter your name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(userName == null || userName.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter a username.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(password == null || password.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(confirmPassword == null || password.trim().length() == 0)
                {
                    Toast.makeText(SignUpActivity.this, "Please confirm your password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!password.equals(confirmPassword))
                {
                    Toast.makeText(SignUpActivity.this, "Your passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                AttemptCreateUser(fullName, userName, password);
            }
        });
    }

    private void AttemptCreateUser(String fullName, String userName, String password)
    {
        if(dbHelper.UserExists(userName))
        {
            Toast.makeText(SignUpActivity.this, "The username " + userName + " is already taken.", Toast.LENGTH_SHORT).show();
            return;
        }
        CreateUser(fullName, userName, password);
    }

    private void CreateUser(String fullName, String userName, String password)
    {
        User user = new User(fullName, userName, password);
        dbHelper.InsertUser(user);
        finish();
    }
}