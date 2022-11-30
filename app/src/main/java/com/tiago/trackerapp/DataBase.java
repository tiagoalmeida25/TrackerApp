package com.tiago.trackerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.contract.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DataBase extends AppCompatActivity {
    EditText category, type, value;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    username = data.getStringExtra("username");
                    String login_output = data.getStringExtra("result");

                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("Login Successful");
                    alertDialog.setMessage(login_output);
                }
            }
        });





        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        category = (EditText)findViewById(R.id.etCategory);
        type = (EditText)findViewById(R.id.etType);
        value = (EditText)findViewById(R.id.etValue);

        Button btSeeDatabase = (Button)findViewById(R.id.btSeeDatabase);

        btSeeDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DataBase.this, ShowData.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        });
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