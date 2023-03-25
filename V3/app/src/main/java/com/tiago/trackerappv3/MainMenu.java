package com.tiago.trackerappv3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenu extends AppCompatActivity {

    String username = "";
    Button textButton;
    Button numericalButton;
    Button counterButton;
    Button sliderButton;
    Button dataButton;


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    username = data.getStringExtra("username");
                    String login_output = data.getStringExtra("result");

                    AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                    alertDialog.setTitle("Login Successful");
                    alertDialog.setMessage(login_output);

                    Log.d("User", username);

                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        textButton = findViewById(R.id.text_class_btn);
        numericalButton = findViewById(R.id.numerical_class_btn);
        counterButton = findViewById(R.id.counter_class_btn);
        sliderButton = findViewById(R.id.slider_class_btn);
        dataButton = findViewById(R.id.database_button);

        if (PreferenceUtils.getUsername(this) == null) {
            activityResultLauncher.launch(new Intent(this, Login.class));
        } else {
            if (PreferenceUtils.getUsername(this).equals("")) {
                activityResultLauncher.launch(new Intent(this, Login.class));
            }
            username = PreferenceUtils.getUsername(this);

            Toast.makeText(this, "Welcome back, " + username + "!", Toast.LENGTH_LONG).show();
        }

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, TextActivity.class);
                startActivity(intent);
            }
        });

        numericalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, NumericalActivity.class);
                startActivity(intent);
            }
        });

        counterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, CounterActivity.class);
                startActivity(intent);
            }
        });

        sliderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, SliderActivity.class);
                startActivity(intent);
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu.this, DataActivity.class);
                startActivity(intent);
            }
        });
    }
}