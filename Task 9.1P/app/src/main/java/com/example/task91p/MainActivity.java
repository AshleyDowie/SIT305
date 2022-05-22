package com.example.task91p;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button newAdvertButton;
    Button showAdvertsButton;
    Button showOnMapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newAdvertButton = findViewById(R.id.newAdvertButton);
        showAdvertsButton = findViewById(R.id.showAdvertsButton);
        showOnMapButton = findViewById(R.id.showOnMapButton);

        newAdvertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newAdvertIntent = new Intent(MainActivity.this, CreateAdvertActivity.class);
                startActivity(newAdvertIntent);
            }
        });

        showAdvertsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showAdvertIntent = new Intent(MainActivity.this, ShowAdvertsActivity.class);
                startActivity(showAdvertIntent);
            }
        });

        showOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showOnMapIntent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(showOnMapIntent);
            }
        });
    }
}