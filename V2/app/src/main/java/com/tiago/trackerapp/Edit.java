package com.tiago.trackerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Edit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    String value, category, type, username, date;

    BroadcastReceiverEditService broadcastReceiver = new BroadcastReceiverEditService();
    String categories = "";
    String types = "";
    String str_category = "Select Category";
    String str_type = "Select Type";
    EditText value_text, addCategory, addType;
    Spinner category_spinner, type_spinner;
    List<String> items_categories = new ArrayList<String>();
    List<String> items_types = new ArrayList<String>();

    private TextView dateView;
    private Button setTime;
    private int year, month, day,hour,minute,second;
    private int myYear, myMonth, myDay,myHour,myMinute,mySecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        onRegister();

        Bundle data = getIntent().getExtras();
        username = data.getString("username");
        value = data.getString("value");
        date = data.getString("date");
        type = data.getString("type");
        category = data.getString("category");

        Log.d("Edit", username + " " + value + " " + date + " " + type + " " + category);

        BackgroundWorker backgroundWorkerCategories = new BackgroundWorker(getApplicationContext());
        backgroundWorkerCategories.execute("get categories", username);

        value_text = findViewById(R.id.etValueEdit);
        value_text.setText(value);

        dateView = findViewById(R.id.dateEdit);
        dateView.setText(date);
        setTime = findViewById(R.id.btSetTimeEdit);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog((Context) Edit.this, (DatePickerDialog.OnDateSetListener) Edit.this, year, month, day);

                datePickerDialog.show();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        myYear = year;
        myDay = day;
        myMonth = month + 1;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Edit.this, Edit.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        myHour = hourOfDay;
        myMinute = minute;
        String time = myDay + "/" + myMonth + "/" + myYear + " " + myHour + ":" + myMinute;
        dateView.setText(time);
    }


    private void onRegister() {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("com.tiago.broadcast.GET_CATEGORIES");
        intentFilter.addAction("com.tiago.broadcast.GET_TYPES");
        intentFilter.addAction("com.tiago.broadcast.NO_CATEGORIES");
        intentFilter.addAction("com.tiago.broadcast.NO_TYPES");
        intentFilter.addAction("com.tiago.broadcast.SAVE");

        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class BroadcastReceiverEditService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action!=null) {
                switch (action){
                    case "com.tiago.broadcast.GET_CATEGORIES":
                        categories = intent.getStringExtra("categories");
                        Log.d("Broadcast categories", categories);

                        category_spinner = (Spinner) findViewById(R.id.spCategoryEdit);
                        type_spinner = (Spinner) findViewById(R.id.spTypeEdit);
                        value_text = (EditText) findViewById(R.id.etValueEdit);
                        addCategory = (EditText) findViewById(R.id.etAddCategoryEdit);
                        addType = (EditText) findViewById(R.id.etAddTypeEdit);

                        String initial = category;

                        String[] categories_string = categories.split("##");

                        items_categories.clear();
                        items_categories.add(initial);
                        items_categories.addAll(Arrays.asList(categories_string));

                        ArrayAdapter<String> adapter_categories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_categories);
                        category_spinner.setAdapter(adapter_categories);

                        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                str_category = items_categories.get(position);

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

                        String initial_type = type;
                        String[] types_string = types.split("--");

                        items_types.clear();
                        if(str_category.equals(category)){
                            items_types.add(initial_type);
                        }
                        else{
                            items_types.add("Select Type");
                        }

                        items_types.addAll(Arrays.asList(types_string));

                        ArrayAdapter<String> adapter_types = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_types);
                        type_spinner.setAdapter(adapter_types);

                        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                                str_type = items_types.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "com.tiago.broadcast.NO_CATEGORIES":
                        String result = intent.getStringExtra("categories");
                        Log.d("Broadcast categories", result);

                        value_text = (EditText) findViewById(R.id.etValueEdit);
                        addCategory = (EditText) findViewById(R.id.etAddCategoryEdit);
                        addType = (EditText) findViewById(R.id.etAddTypeEdit);

                        break;
                    case "com.tiago.broadcast.NO_TYPES":
                        String result_type = intent.getStringExtra("types");
                        Log.d("Broadcast types", result_type);

                        value_text = (EditText) findViewById(R.id.etValueEdit);
                        addCategory = (EditText) findViewById(R.id.etAddCategoryEdit);
                        addType = (EditText) findViewById(R.id.etAddTypeEdit);

                        break;
                }
            }
        }
    }




    public void OnSaveEdit(View view) {
        String str_add_category = addCategory.getText().toString();
        String str_add_type = addType.getText().toString();
        String category_final = "";
        String type_final = "";
        boolean save = true;

        if(str_add_category.isEmpty()){
            if(str_category.equals(category)){
                category_final = category;
            }
            else{
               category_final = str_category;
            }
        }
        else{
            category_final = str_add_category;
        }

        if(str_add_type.isEmpty()){
            if(str_type.equals(type)){
                type_final = type;
            }
            else{
                if(str_type.equals("Choose Type")){
                    android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Save Error");
                    alertDialog.setMessage("Select Type");
                    alertDialog.show();
                    save = false;
                }
                else {
                    type_final = str_type;
                }
            }
        }
        else{
            type_final = str_add_type;
        }

        String new_date = dateView.getText().toString();
        String new_value = value_text.getText().toString();

        if(save){
            Log.d("Edit", category + " " + type + " " + value + " " + date + " " + username);
            Log.d("Edit New", category_final + " " + type_final + " " + new_value + " " + new_date + " " + username);

            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute("delete value", value, date, type, category, username);

            BackgroundWorker backgroundWorkerSave = new BackgroundWorker(this);
            backgroundWorkerSave.execute("save with time", category_final, type_final, new_value, new_date, username);
        }
    }
}
