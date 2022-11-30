package com.tiago.trackerapp;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Dropdown extends AppCompatActivity {
    String categories = "";
    String types = "";
    EditText value;
    Spinner category, type;
    String username = "";
    BroadcastReceiverMainService broadcastReceiver = new BroadcastReceiverMainService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown);

        onRegister();

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

        activityResultLauncher.launch(new Intent(this, Login.class));

        category = findViewById(R.id.spCategory);
        type = findViewById(R.id.spType);
        value = (EditText)findViewById(R.id.etValueV2);

        BackgroundWorker backgroundWorkerCategories = new BackgroundWorker(this);
        backgroundWorkerCategories.execute("get categories", username);

        Log.d("Categories",categories);

        String[] items_categories = categories.split(":");
        ArrayAdapter<String> adapter_categories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_categories);
        category.setAdapter(adapter_categories);

        category.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str_category = items_categories[i];

                BackgroundWorker backgroundWorkerTypes = new BackgroundWorker(getApplicationContext());
                backgroundWorkerTypes.execute("get types",str_category);

                String[] items_types = types.split("--");
                ArrayAdapter<String> adapter_types = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, items_types);
                type.setAdapter(adapter_types);

                type.setOnItemSelectedListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String str_type = items_types[i];

                        BackgroundWorker backgroundWorkerTypes = new BackgroundWorker(getApplicationContext());
                        backgroundWorkerTypes.execute("save",str_category,str_type, username);
                    }
                });
            }
        });


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
                    Log.d("categories", categories);
                    break;
                case "com.tiago.broadcast.GET_TYPES":
                    types = intent.getStringExtra("types");
                    break;
                }
            }

        }


    }

}