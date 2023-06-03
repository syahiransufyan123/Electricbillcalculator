package com.example.billcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText unitsEditText;
    private EditText rebateEditText;
    private Button calculateButton;
    private TextView resultTextView;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unitsEditText = findViewById(R.id.unitsEditText);
        rebateEditText = findViewById(R.id.rebateEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);
        clearButton = findViewById(R.id.clearButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBill();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputFields();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                navigateToAboutPage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void calculateBill() {
        String unitsInput = unitsEditText.getText().toString();
        String rebateInput = rebateEditText.getText().toString();

        if (unitsInput.isEmpty() || rebateInput.isEmpty()) {
            Toast.makeText(this, "Please enter values for all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(unitsInput);
        double rebate = Double.parseDouble(rebateInput);

        if (units < 1 || units > 900 || rebate < 0 || rebate > 5) {
            resultTextView.setText("Invalid input. Please enter valid values.");
            return;
        }

        double totalCharges = 0;

        if (units <= 200) {
            totalCharges = units * 0.218;
        } else if (units <= 300) {
            totalCharges = 200 * 0.218 + (units - 200) * 0.334;
        } else if (units <= 600) {
            totalCharges = 200 * 0.218 + 100 * 0.334 + (units - 300) * 0.516;
        } else if (units > 600) {
            totalCharges = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (units - 600) * 0.546;
        }

        double finalCost = totalCharges - (totalCharges * (rebate /100) );

        resultTextView.setText("Total Charges: RM " + totalCharges + "\nFinal Cost: RM " + finalCost);
    }

    private void clearInputFields() {
        unitsEditText.setText("");
        rebateEditText.setText("");
        resultTextView.setText("");
    }

    private void navigateToAboutPage() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }
}
