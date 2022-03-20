package com.example.task31c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    TextView congratulationsTextView;
    TextView scoreTextView;
    Button takeNewQuizButton;
    Button finishButton;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        congratulationsTextView = findViewById(R.id.congratulationsTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        takeNewQuizButton = findViewById(R.id.takeNewQuizButton);
        finishButton = findViewById(R.id.finishButton);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        int correctAnswers = intent.getIntExtra("correctAnswers", 0);
        int totalQuestions = intent.getIntExtra("totalQuestions", 0);

        congratulationsTextView.setText("Congratulations " + name + "!");
        scoreTextView.setText(String.valueOf(correctAnswers) + "/" + String.valueOf(totalQuestions));

        takeNewQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMainActivity();
                finish();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }

    private void OpenMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("startName", name);
        startActivity(intent);
    }
}