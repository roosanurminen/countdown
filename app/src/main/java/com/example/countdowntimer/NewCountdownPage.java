package com.example.countdowntimer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;
import java.util.Calendar;

public class NewCountdownPage extends Activity {
    private TextView textView;
    private EditText editText;
    private Button saveButton;
    private String selectedDate = "";
    private String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_countdown);

        editText = findViewById(R.id.titleEditText);
        textView = findViewById(R.id.datePicker);
        saveButton = findViewById(R.id.addButton);

        saveButton.setEnabled(false);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewCountdownPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = dayOfMonth + "." + (month + 1) + "." + year;
                        textView.setText(selectedDate);
                    }
                }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() /*- 1000*/);
                datePickerDialog.show();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                eventTitle = editText.getText().toString();
                saveButton.setEnabled(!eventTitle.isEmpty() && !selectedDate.isEmpty());
            }
        };

        textView.addTextChangedListener(textWatcher);
        editText.addTextChangedListener(textWatcher);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventTitle = editText.getText().toString();
                selectedDate = textView.getText().toString();

                if (eventTitle.length() > 20) {
                    Toast.makeText(getApplicationContext(), "Title too long!", Toast.LENGTH_SHORT).show();
                    editText.getText().clear();
                    return;
                }

                JsonHandler.saveDataToJson(NewCountdownPage.this, eventTitle, selectedDate);
                editText.getText().clear();
                textView.setText("");
                selectedDate = "";
                Toast.makeText(getApplicationContext(), "Countdown successfully added!", Toast.LENGTH_SHORT).show();
                saveButton.setEnabled(false);
            }
        });
    }
}