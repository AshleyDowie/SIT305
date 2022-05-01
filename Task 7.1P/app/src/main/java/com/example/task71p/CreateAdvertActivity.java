package com.example.task71p;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.example.task71p.data.DatabaseHelper;
import com.example.task71p.model.Advert;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateAdvertActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    RadioButton lostRadioButton;
    RadioButton foundButton;
    EditText nameEditText;
    EditText phoneEditText;
    EditText descriptionEditText;
    EditText dateEditText;
    EditText locationEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DatabaseHelper(this);

        lostRadioButton = findViewById(R.id.lostRadioButton);
        foundButton = findViewById(R.id.foundButton);
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        dateEditText = findViewById(R.id.dateEditText);
        locationEditText = findViewById(R.id.locationEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!ValidPostType())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please select a post type."
                            , Toast.LENGTH_SHORT).show();
                }
                else if(!ValidName())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a name of at least 3 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidPhone())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a phone number of at least 8 digits.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDescription())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a description of at least 10 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidDate())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a date in the format dd/MM/yyyy",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!ValidLocation())
                {
                    Toast.makeText(CreateAdvertActivity.this, "Please enter a location with at least 4 characters.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    SaveAdvert();
                    finish();
                }
            }
        });
    }

    private void SaveAdvert()
    {
        Advert advert = new Advert(nameEditText.getText().toString(), phoneEditText.getText().toString(),
                descriptionEditText.getText().toString(), dateEditText.getText().toString(), locationEditText.getText().toString(),
                GetPostType());

        dbHelper.InsertAdvert(advert);
    }

    private String GetPostType()
    {
        if(lostRadioButton.isChecked())
        {
            return lostRadioButton.getText().toString();
        }
        else
        {
            return foundButton.getText().toString();
        }
    }

    private boolean ValidPostType()
    {
        return lostRadioButton.isChecked() || foundButton.isChecked();
    }

    private boolean ValidName()
    {
        String name = nameEditText.getText().toString();
        return name != null && name.trim().length() > 2;
    }

    private boolean ValidPhone()
    {
        String phone = phoneEditText.getText().toString();
        return phone != null && phone.trim().length() > 7;
    }

    private boolean ValidDescription()
    {
        String description = descriptionEditText.getText().toString();
        return description != null && description.trim().length() > 9;
    }

    private boolean ValidDate()
    {
        String dateString = dateEditText.getText().toString();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateFormat.setLenient(false);
            dateFormat.parse(dateString);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean ValidLocation()
    {
        String location = locationEditText.getText().toString();
        return location != null && location.trim().length() > 3;
    }
}