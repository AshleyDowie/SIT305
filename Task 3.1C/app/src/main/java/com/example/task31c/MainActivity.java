package com.example.task31c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        startButton = findViewById(R.id.startButton);
        nameText = findViewById(R.id.nameText);

        String startName = intent.getStringExtra("startName");
        nameText.setText(startName);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartQuiz();
            }
        });
    }

    private void StartQuiz()
    {
        String name = nameText.getText().toString();
        if(name == null || name.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Please enter your name", Toast.LENGTH_LONG).show();
            return;
        }
        OpenQuestionActivity(name);
    }

    private void OpenQuestionActivity(String name)
    {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
        finish();
    }
}