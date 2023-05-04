package com.tiago.trackerappv3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextActivity extends AppCompatActivity {
    String username = "";
    String categories = "";
    String types = "";
    String str_category = "Select Category";
    String str_type = "Select Type";
    EditText value, addCategory,addType;
    Spinner category, type;
    List<String> items_categories = new ArrayList<String>();
    List<String> items_types = new ArrayList<String>();

    private TextView dateView;
    private Button setTime;
    private int year, month, day,hour,minute,second;
    private int myYear, myMonth, myDay,myHour,myMinute,mySecond;

    BroadcastReceiverTextService broadcastReceiver = new BroadcastReceiverTextService();
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
        }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
    }


    private class BroadcastReceiverTextService extends BroadcastReceiver {
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

                        String initial = "Select Category";

                        String[] categories_string = categories.split("##");

                        items_categories.clear();
                        items_categories.add(initial);
                        items_categories.addAll(Arrays.asList(categories_string));

                        ArrayAdapter<String> adapter_categories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_categories);
                        category.setAdapter(adapter_categories);

                        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                str_category = "Select Category";
                                if(position != 0) {
                                    str_category = items_categories.get(position);

                                    BackgroundWorker backgroundWorkerTypes = new BackgroundWorker(getApplicationContext());
                                    backgroundWorkerTypes.execute("get types", str_category);
                                }
                                else{
                                    type.setAdapter(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        break;
                    case "com.tiago.broadcast.GET_TYPES":
                        types = intent.getStringExtra("types");

                        String initial_type = "Select Type";
                        String[] types_string = types.split("--");

                        items_types.clear();
                        items_types.add(initial_type);
                        items_types.addAll(Arrays.asList(types_string));

                        ArrayAdapter<String> adapter_types = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_types);
                        type.setAdapter(adapter_types);

                        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                                str_type = "Select Type";
                                if(position != 0) {
                                    str_type = items_types.get(position);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }


                        });
                        break;
                    case "com.tiago.broadcast.NO_CATEGORIES":
                        String result = intent.getStringExtra("categories");
                        Log.d("Broadcast categories", result);

                        value = (EditText) findViewById(R.id.etValueV2);
                        addCategory = (EditText) findViewById(R.id.etAddCategory);
                        addType = (EditText) findViewById(R.id.etAddType);

                        break;
                    case "com.tiago.broadcast.NO_TYPES":
                        String result_type = intent.getStringExtra("types");
                        Log.d("Broadcast types", result_type);

                        value = (EditText) findViewById(R.id.etValueV2);
                        addCategory = (EditText) findViewById(R.id.etAddCategory);
                        addType = (EditText) findViewById(R.id.etAddType);

                        break;
                }
            }
        }
    }
}