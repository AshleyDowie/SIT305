package com.example.task21p;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Spinner unitSpinner;
    EditText amountNumber;
    ImageButton lengthButton;
    ImageButton temperatureButton;
    ImageButton weightButton;
    ListView conversionListView;
    ArrayList unitArrayList;
    ArrayAdapter unitArrayAdapter;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#,###.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountNumber = findViewById(R.id.amountNumber);
        SetImageButtons();
        SetSpinnerValues();
        SetUnitListView();
    }

    private void SetSpinnerValues()
    {
        unitSpinner = findViewById(R.id.unitSpinner);
        unitSpinner.setAdapter(ArrayAdapter.createFromResource(
                this,
                R.array.unit_values,
                android.R.layout.simple_spinner_item));
    }

    private void SetUnitListView()
    {
        conversionListView = findViewById(R.id.conversionListView);
        unitArrayList = new ArrayList<String>();
        unitArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, unitArrayList);
        conversionListView.setAdapter(unitArrayAdapter);
    }

    private void SetImageButtons()
    {
        lengthButton = findViewById(R.id.lengthButton);
        temperatureButton = findViewById(R.id.temperatureButton);
        weightButton = findViewById(R.id.weightButton);

        lengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RespondToClick(view);
            }
        });

        temperatureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RespondToClick(view);
            }
        });

        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RespondToClick(view);
            }
        });
    }

    private void RespondToClick(View view)
    {
        switch(view.getId()) {
            case R.id.lengthButton:
            {
                if(unitSpinner.getSelectedItem().toString().trim().equals("Metre"))
                {
                    DisplayLength();
                }
                else
                {
                    DisplayError();
                }
            }
                break;
            case R.id.temperatureButton:
            {
                if(unitSpinner.getSelectedItem().toString().trim().equals("Celsius"))
                {
                    DisplayTemperature();
                }
                else
                {
                    DisplayError();
                }
            }
            break;
            case R.id.weightButton:
            {
                if(unitSpinner.getSelectedItem().toString().equals("Kilograms"))
                {
                    DisplayWeight();
                }
                else
                {
                    DisplayError();
                }
            }
            break;
        }
    }

    private void DisplayError()
    {
        Toast.makeText(MainActivity.this, "Please select the correct conversion icon.", Toast.LENGTH_LONG).show();
    }


    private void DisplayLength()
    {
        Double amount = GetAmount();
        unitArrayList.clear();
        unitArrayList.add(decimalFormat.format(amount * 100) + " Centimeter");
        unitArrayList.add(decimalFormat.format(amount * 3.28084) + " Foot");
        unitArrayList.add(decimalFormat.format(amount * 39.3701) + " Inch");
        unitArrayAdapter.notifyDataSetChanged();
    }

    private void DisplayTemperature()
    {
        Double amount = GetAmount();
        unitArrayList.clear();
        unitArrayList.add(decimalFormat.format(amount * 1.8 + 32) + " Fahrenheit");
        unitArrayList.add(decimalFormat.format(amount + 273.15) + " Kelvin");
        unitArrayAdapter.notifyDataSetChanged();
    }

    private void DisplayWeight()
    {
        Double amount = GetAmount();
        unitArrayList.clear();
        unitArrayList.add(decimalFormat.format(amount * 1000) + " Gram");
        unitArrayList.add(decimalFormat.format(amount * 35.274) + " Ounce");
        unitArrayList.add(decimalFormat.format(amount * 2.20462) + " Pound");
        unitArrayAdapter.notifyDataSetChanged();
    }

    private Double GetAmount()
    {
        Double amount = 0.00;

        try {
            amount = Double.parseDouble(amountNumber.getText().toString());
        }
        finally {
            return amount;
        }
    }
}