package com.tiago.trackerapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;

public class Dropdown extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    String username = "";
    String categories = "";
    String types = "";
    String str_category = "";
    String str_type = "";
    EditText value, addCategory,addType;
    Spinner category, type;
    String[] items_categories;
    String[] items_types;

    private TextView dateView;
    private Button setTime;
    private int year, month, day,hour,minute,second;
    private int myYear, myMonth, myDay,myHour,myMinute,mySecond;


    BroadcastReceiverMainService broadcastReceiver = new BroadcastReceiverMainService();
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

                    BackgroundWorker backgroundWorkerCategories = new BackgroundWorker(getApplicationContext());
                    backgroundWorkerCategories.execute("get categories", username);
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown);

        onRegister();
        Log.d("On create","on create");

        activityResultLauncher.launch(new Intent(this, Login.class));

        dateView = findViewById(R.id.dateView);
        setTime = findViewById(R.id.btSetTime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Dropdown.this, Dropdown.this,year, month, day);

                datePickerDialog.show();
            }
        });



//        Button btSeeDatabase = (Button)findViewById(R.id.btSeeDatabaseDrop);
//
//        btSeeDatabase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d("User in Database",username);
//
//                Intent i = new Intent(Dropdown.this, ShowData.class);
//                i.putExtra("username", username);
//                startActivity(i);
//            }
//        });
    }

    private void onRegister() {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("com.tiago.broadcast.GET_CATEGORIES");
        intentFilter.addAction("com.tiago.broadcast.GET_TYPES");
        intentFilter.addAction("com.tiago.broadcast.SAVE");

        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class BroadcastReceiverMainService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action!=null) {
                switch (action){
                case "com.tiago.broadcast.GET_CATEGORIES":
                    categories = intent.getStringExtra("categories");
                    Log.d("Broadcast categories", categories);

                    category = (Spinner) findViewById(R.id.spCategory);
                    type = (Spinner) findViewById(R.id.spType);
                    value = (EditText) findViewById(R.id.etValueV2);
                    addCategory = (EditText) findViewById(R.id.etAddCategory);
                    addType = (EditText) findViewById(R.id.etAddType);

                    Log.d("Categories", categories);

                    items_categories = categories.split(":");
                    ArrayAdapter<String> adapter_categories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_categories);
                    category.setAdapter(adapter_categories);

                    category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            str_category = items_categories[position];
                            Log.d("Category", str_category);
                            Log.d("Item Category", items_categories[position]);

                            BackgroundWorker backgroundWorkerTypes = new BackgroundWorker(getApplicationContext());
                            backgroundWorkerTypes.execute("get types", str_category);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case "com.tiago.broadcast.GET_TYPES":
                    types = intent.getStringExtra("types");


                    items_types = types.split("--");
                    ArrayAdapter<String> adapter_types = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_types);
                    type.setAdapter(adapter_types);

                    type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {
                                str_type = items_types[position];
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }


                    });
                    break;
                }
            }
        }
    }

    public void OnSaveDropDown(View view) {
        String str_value = value.getText().toString();
        String str_add_category = addCategory.getText().toString();
        String str_add_type = addType.getText().toString();
        String mode = "save";
        String category_final = "";
        String type_final = "";

        String date = dateView.getText().toString();

        value.setText("");
        addCategory.setText("");
        addType.setText("");

        if(str_add_category.isEmpty()){
            // old category
            category_final = str_category;
            if(str_add_type.isEmpty()){
                // old type
                type_final = str_type;
            }
            else {
                // new type for old category
                type_final = str_add_type;
            }
        }
        else{
        // new category with the new type
            category_final = str_add_category;
            type_final = str_add_type;

        }

        if(date.isEmpty()) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(mode, category_final, type_final, str_value, username);
        }
        else{
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("save with time", category_final, type_final, str_value, date, username);
        }
    }
    public void OnSeeDataBase(View view) {
        String str_add_category = addCategory.getText().toString();
        String str_add_type = addType.getText().toString();
        String mode = "display values";
        String category_final = "";
        String type_final = "";

        value.setText("");
        addCategory.setText("");
        addType.setText("");

        if(str_add_category.isEmpty()){
            // old category
            category_final = str_category;
            if(str_add_type.isEmpty()){
                // old type
                type_final = str_type;
            }
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Database");
                alertDialog.setMessage("New Type");
            }
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
            alertDialog.setTitle("Database");
            alertDialog.setMessage("New Category");

        }

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(mode, type_final);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        myYear = year;
        myDay = day;
        myMonth = month + 1;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Dropdown.this, Dropdown.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        String time = myYear + "/" + myMonth + "/" + myDay + " " + myHour + ":" + myMinute;
        dateView.setText(time);
    }
}