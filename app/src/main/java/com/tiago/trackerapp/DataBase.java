package com.tiago.trackerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class DataBase extends AppCompatActivity {
    EditText category, type, value;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        category = (EditText)findViewById(R.id.etCategory);
        type = (EditText)findViewById(R.id.etType);
        value = (EditText)findViewById(R.id.etValue);
    }

    public void OnSave(View view) {
        String str_category = category.getText().toString();
        String str_type = type.getText().toString();
        String str_value = value.getText().toString();
        String mode = "save";

        category.setText("");
        type.setText("");
        value.setText("");

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(mode, str_category, str_type, str_value, username);
    }
}