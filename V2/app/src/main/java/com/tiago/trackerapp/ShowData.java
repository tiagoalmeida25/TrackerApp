package com.tiago.trackerapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowData extends AppCompatActivity {

    EditText getCategory, getType;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        getCategory = (EditText) findViewById(R.id.etChooseCategory);
        getType = (EditText)findViewById(R.id.etChooseType);

        Button btBackShowdata = (Button)findViewById(R.id.btBackShowdata);

        btBackShowdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void OnGetCategories(View view){
        String type = "display categories";
        Log.d("user",username);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, username);
    }
    public void OnGetTypes(View view){
        String str_category = getCategory.getText().toString();
        String type = "display types";

        if (str_category.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(ShowData.this).create();
            alertDialog.setTitle("Category");
            alertDialog.setMessage("No Category Selected");
            alertDialog.show();
        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_category);
    }
    public void OnGetValues(View view){
        String str_type = getType.getText().toString();
        String type = "display values";

        if (str_type.isEmpty()){
            AlertDialog alertDialog = new AlertDialog.Builder(ShowData.this).create();
            alertDialog.setTitle("Type");
            alertDialog.setMessage("No Type Selected");
        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, str_type);
    }
}