package com.example.task71p;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button newAdvertButton;
    Button showAdvertsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newAdvertButton = findViewById(R.id.newAdvertButton);
        showAdvertsButton = findViewById(R.id.showAdvertsButton);

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
    }
}