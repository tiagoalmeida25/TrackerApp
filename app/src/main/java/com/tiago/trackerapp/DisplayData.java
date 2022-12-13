package com.tiago.trackerapp;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayData extends AppCompatActivity {
    String username = "";
    String categories = "";
    String types = "";
    String values = "";

    String category = "";
    String type = "";
    BroadcastReceiverDisplayService broadcastReceiver = new BroadcastReceiverDisplayService();
    ListView listview = null;

    List<String> name = new ArrayList<String>();
    List<String> date = new ArrayList<String>();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        Bundle data = getIntent().getExtras();
        username = data.getString("username");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        onRegister();
//
//        listview = (ListView) findViewById(R.id.listview);
//
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute("display categories", username);

    }


    private void onRegister() {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction("com.tiago.broadcast.DISPLAY_CATEGORIES");
        intentFilter.addAction("com.tiago.broadcast.DISPLAY_TYPES");
        intentFilter.addAction("com.tiago.broadcast.DISPLAY_VALUES");

        registerReceiver(broadcastReceiver, intentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private class BroadcastReceiverDisplayService extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case "com.tiago.broadcast.DISPLAY_CATEGORIES":
                        categories = intent.getStringExtra("categories");

                        String[] categories_string = categories.split("»»");

//                        ArrayAdapter<String> adapter_categories = new ArrayAdapter<String>(DisplayData.this, android.R.layout.simple_list_item_1, categories_string);
//                        listview.setAdapter(adapter_categories);
//
//                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                                category = categories_string[position];
//
//                                Log.d("Database category", category);
//
//                                BackgroundWorker backgroundWorker = new BackgroundWorker(DisplayData.this);
//                                backgroundWorker.execute("display types", category);
//
//                            }
//                        });


                        break;

                    case "com.tiago.broadcast.DISPLAY_TYPES":
                        types = intent.getStringExtra("types");

                        String[] types_string = types.split("~~");

                        ArrayAdapter<String> adapter_types = new ArrayAdapter<String>(DisplayData.this, android.R.layout.simple_list_item_1, types_string);
                        listview.setAdapter(adapter_types);

                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                                type = types_string[position];

                                Log.d("Database type", type);

                                BackgroundWorker backgroundWorker = new BackgroundWorker(DisplayData.this);
                                backgroundWorker.execute("display values", category, type);
                            }
                        });

                        break;

                    case "com.tiago.broadcast.DISPLAY_VALUES":
                        values = intent.getStringExtra("values");

                        String[] values_string = values.split("ºº");

//                        Log.d("Database values", values);
//
//                        ArrayAdapter<String> adapter_values = new ArrayAdapter<String>(DisplayData.this, android.R.layout.simple_list_item_1, values_string);
//                        listview.setAdapter(adapter_values);
                        name.clear();
                        date.clear();
                        for(int i = 0; i< values_string.length;i++) {
                            String[] item_db = values_string[i].split("-");
                            name.add(item_db[0]);
                            date.add(item_db[1]);
                        }
                        ArrayList<Item> itemArrayList = new ArrayList<>();
                        for(int i = 0; i< values_string.length;i++){
                            Item item = new Item(name.get(i), date.get(i));
                            itemArrayList.add(item);
                        }

                        ListAdapter listAdapter = new ListAdapter(DisplayData.this, itemArrayList);
                        binding.listview.setAdapter(listAdapter);
                        binding.listview.setClickable(true);
                        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                            Intent i = new Intent(DisplayData.this,Item.class);
                            i.putExtra("name",name[position]);
                            i.putExtra("date",date[position]);
                            i.putExtra("type",type);
                            i.putExtra("category",category);
                            startActivity(i);
                            });




                        break;
                }
            }
        }
    }
}