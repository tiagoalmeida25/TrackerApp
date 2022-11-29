package com.tiago.trackerapp;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class Dropdown extends AppCompatActivity {

    String categories = "";
    String types = "";
    EditText value;
    Spinner category, type;
    String username = "";
    private BroadcastReceiverMainService broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");

        category = findViewById(R.id.spCategory);
        type = findViewById(R.id.spType);
        value = (EditText)findViewById(R.id.etValueV2);

        onRegister("com.tiago.broadcast.GET_CATEGORIES");

        BackgroundWorker backgroundWorkerCategories = new BackgroundWorker(this);
        backgroundWorkerCategories.execute("get categories", username);

        Log.d("Categories",categories);
        Log.d("Here","here");

        String[] items_categories = categories.split(":");
        ArrayAdapter<String> adapter_categories = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_categories);
        category.setAdapter(adapter_categories);

        // Estas opcoes so aparecem depois de escolher a categoria

//        onRegister("com.tiago.broadcast.GET_TYPES");
//        BackgroundWorker backgroundWorkerTypes = new BackgroundWorker(this);
//        backgroundWorkerTypes.execute("get types",category_selected);

//        String[] items_types = types.split("++");
//        ArrayAdapter<String> adapter_types = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items_types);
//        category.setAdapter(adapter_types);
    }

    private void onRegister(String flag) {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(flag);
//        intentFilter.addAction("com.tiago.broadcast.GET_TYPES");

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
            if (action.equals("com.tiago.broadcast.GET_CATEGORIES")) {
                categories = intent.getStringExtra("categories");
                Log.d("categories",categories);
            }
            else if (action.equals("com.tiago.broadcast.GET_TYPES")) {
                types = intent.getStringExtra("types");
            }
            else{
                Log.d("idk","idk");
            }

        }


    }

}