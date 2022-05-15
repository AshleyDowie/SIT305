package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.User;
import com.example.task81c.model.Video;

public class HomePageActivity extends AppCompatActivity {

    int userId;
    DatabaseHelper dbHelper;
    EditText homeURLEditText;
    Button homePlayButton;
    Button homeAddButton;
    Button homeListButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        dbHelper = new DatabaseHelper(this);
        homeURLEditText = findViewById(R.id.homeURLEditText);
        homePlayButton = findViewById(R.id.homePlayButton);
        homeAddButton = findViewById(R.id.homeAddButton);
        homeListButton = findViewById(R.id.homeListButton);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        String url = intent.getStringExtra("url");

        if(url != null)
        {
            homeURLEditText.setText(url);
        }

        homePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = homeURLEditText.getText().toString();

                if(url == null || url.trim().length() == 0)
                {
                    Toast.makeText(HomePageActivity.this, "Please enter a url for a youtube video " +
                                    "or select one from your playlist",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                OpenPlayActivity(url);
            }
        });

        homeAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = homeURLEditText.getText().toString();

                if(url == null || url.trim().length() == 0)
                {
                    Toast.makeText(HomePageActivity.this, "Please enter a url for a youtube video.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                AttemptCreateVideo(url);
            }
        });

        homeListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenPlayListActivity();
            }
        });
    }

    private void AttemptCreateVideo(String url)
    {
        if(dbHelper.VideoExists(url, userId))
        {
            Toast.makeText(HomePageActivity.this, "The video url " + url + " is already in your playlist.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        CreateVideo(url);
    }

    private void CreateVideo(String url)
    {
        Video video = new Video(userId, url);
        dbHelper.InsertVideo(video);
        Toast.makeText(HomePageActivity.this, "You have added " + url + " to your playlist.",
                Toast.LENGTH_SHORT).show();
    }

    public void OpenPlayListActivity()
    {
        Intent playlistIntent = new Intent(HomePageActivity.this, PlayListActivity.class);
        playlistIntent.putExtra("userId", userId);
        startActivity(playlistIntent);
    }

    public void OpenPlayActivity(String url)
    {
        Intent playIntent = new Intent(HomePageActivity.this, PlayActivity.class);
        playIntent.putExtra("url", url);
        startActivity(playIntent);
    }
}