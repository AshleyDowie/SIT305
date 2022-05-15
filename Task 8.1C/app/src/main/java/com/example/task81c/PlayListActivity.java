package com.example.task81c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.task81c.data.DatabaseHelper;
import com.example.task81c.model.Video;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {

    int userId;
    DatabaseHelper dbHelper;
    ListView videoListView;
    ArrayList videoArrayList;
    VideoAdapter videoArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", -1);

        videoListView = findViewById(R.id.videoListView);

        videoArrayList = dbHelper.GetVideos(userId);
        videoArrayAdapter = new VideoAdapter(this, videoArrayList);
        videoListView.setAdapter(videoArrayAdapter);

        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Video video = videoArrayAdapter.getItem(i);

                Intent homePageIntent = new Intent(PlayListActivity.this, HomePageActivity.class);
                homePageIntent.putExtra("userId", userId);
                homePageIntent.putExtra("url", video.getVideoURL());
                startActivity(homePageIntent);
                finish();
            }
        });
    }
}