package com.example.task31c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    String name;
    TextView welcomeTextView;
    TextView progressTextView;
    ProgressBar progressBar;
    int questionNumber;
    int correctAnswers;

    TextView questionTitleTextView;
    TextView questionDetailsTextView;
    Button answer1Button;
    Button answer2Button;
    Button answer3Button;
    Button submitButton;
    Question questions[];
    Button[] answers;
    Button selectedAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        questionNumber = 0;
        correctAnswers = 0;
        progressTextView = findViewById(R.id.progressTextView);
        progressBar = findViewById(R.id.progressBar);

        questionTitleTextView = findViewById(R.id.questionTitleTextView);
        questionDetailsTextView = findViewById(R.id.questionDetailsTextView);
        answer1Button = findViewById(R.id.answer1Button);
        answer2Button = findViewById(R.id.answer2Button);
        answer3Button = findViewById(R.id.answer3Button);
        submitButton = findViewById(R.id.submitButton);

        answers = new Button[]{answer1Button, answer2Button, answer3Button};

        for (Button answer : answers)
        {
            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SelectAnswer((Button)view);
                }
            });
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(submitButton.getText().toString().equals("Submit"))
                {
                    SubmitAnswer();
                }
                else if (questionNumber < questions.length)
                {
                    DisplayNewQuestion();
                }
                else
                {
                    OpenResultActivity();
                }
            }
        });

        SetWelcomeMessage();
        CreateQuestions();
        DisplayNewQuestion();
    }

    private void OpenResultActivity()
    {
        Intent intent = new Intent(this, ResultsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("correctAnswers", correctAnswers);
        intent.putExtra("totalQuestions", questions.length);
        startActivity(intent);
        finish();
    }

    private void SelectAnswer(Button button)
    {
        if(selectedAnswer != null) {
            selectedAnswer.setBackgroundColor(Color.WHITE);
        }
        selectedAnswer = button;
        selectedAnswer.setBackgroundColor(Color.YELLOW);
    }

    private void SubmitAnswer()
    {

        if(selectedAnswer == null)
        {
            Toast.makeText(QuestionActivity.this, "Please select an answer", Toast.LENGTH_LONG).show();
            return;
        }

        String correctAnswer = questions[questionNumber-1].CorrectAnswer;
        String givenAnswer = selectedAnswer.getText().toString();

        if(givenAnswer.equals(correctAnswer))
        {
            correctAnswers++;
            selectedAnswer.setBackgroundColor(Color.GREEN);
        }
        else
        {
            selectedAnswer.setBackgroundColor(Color.RED);
            for (Button answer : answers)
            {
                if (answer.getText().toString().equals(correctAnswer))
                {
                    answer.setBackgroundColor(Color.GREEN);
                }
            }
        }

        selectedAnswer = null;
        submitButton.setText("Next");

        for (Button answer : answers)
        {
            answer.setEnabled(false);
        }
    }

    private void SetWelcomeMessage()
    {
        welcomeTextView = findViewById(R.id.welcomeTextView);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        welcomeTextView.setText("Welcome " + name + "!");
    }

    private void DisplayNewQuestion()
    {
        answer1Button.setText(questions[questionNumber].Answers[0]);
        answer1Button.setBackgroundColor(Color.WHITE);
        answer1Button.setTextColor(Color.BLACK);
        answer2Button.setText(questions[questionNumber].Answers[1]);
        answer2Button.setBackgroundColor(Color.WHITE);
        answer2Button.setTextColor(Color.BLACK);
        answer3Button.setText(questions[questionNumber].Answers[2]);
        answer3Button.setBackgroundColor(Color.WHITE);
        answer3Button.setTextColor(Color.BLACK);
        submitButton.setText("Submit");
        questionTitleTextView.setText(questions[questionNumber].Title);
        questionDetailsTextView.setText(questions[questionNumber].Detail);

        for (Button answer : answers)
        {
            answer.setEnabled(true);
        }

        UpdateProgress();
    }

    private void UpdateProgress()
    {
        questionNumber++;
        progressTextView.setText(String.valueOf(questionNumber) + "/" + String.valueOf(questions.length));
        progressBar.setProgress((int)(((double) questionNumber) / questions.length *100));
    }

    private void CreateQuestions()
    {
        Question question1 = new Question();
        question1.Title = "Subclass of";
        question1.Detail = "TextView, Button and ProgressBar are all subclasses of..";
        question1.Answers = new String[]{"Layout", "View", "Toast"};
        question1.CorrectAnswer = "View";

        Question question2 = new Question();
        question2.Title = "Stands for";
        question2.Detail = "IDE stands for...";
        question2.Answers = new String[]{"Integrated Development Environment",
                "Initial Design Entity", "Instant Data Execution"};
        question2.CorrectAnswer = "Integrated Development Environment";

        Question question3 = new Question();
        question3.Title = "What to use";
        question3.Detail = "If you want to display another activity you should use..";
        question3.Answers = new String[]{"Spinner", "Toast", "Intent"};
        question3.CorrectAnswer = "Intent";

        Question question4 = new Question();
        question4.Title = "API Level";
        question4.Detail = "API Level is an/a ...... value";
        question4.Answers = new String[]{"String", "Integer", "Character"};
        question4.CorrectAnswer = "Integer";

        Question question5 = new Question();
        question5.Title = "Android Version";
        question5.Detail = "Which of the following is not a codename for a version of android";
        question5.Answers = new String[]{"Pie", "Froyo", "Cookie"};
        question5.CorrectAnswer = "Cookie";

        questions = new Question[]{question1, question2, question3, question4, question5};
    }
}