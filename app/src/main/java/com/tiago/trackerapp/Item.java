package com.tiago.trackerapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//public class Item {
//    String name, date, type, category;
//
//    public Item(String name, String date, String type, String category){
//        this.name = name;
//        this.date = date;
//        this.type = type;
//        this.category = category;
//    }
//}

public class Item extends AppCompatActivity {
    String value, category, type, username, date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Bundle data = getIntent().getExtras();
        username = data.getString("username");
        value = data.getString("name");
        date = data.getString("date");
        type = data.getString("type");
        category = data.getString("category");

        TextView item_text = findViewById(R.id.textItem);
        TextView date_text = findViewById(R.id.textItemDate);
        TextView type_text = findViewById(R.id.textItemType);
        TextView category_text = findViewById(R.id.textItemCategory);
        Button btDelete = findViewById(R.id.itemDelete);

        item_text.setText(value);
        date_text.setText(date);
        type_text.setText(type);
        category_text.setText(category);
    }

    public void OnDelete(View view){
        new AlertDialog.Builder(this)
            .setTitle("Delele Value?")
            .setMessage("Are you sure you want to delete?")
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface arg0, int arg1) {
                    Toast.makeText(Item.this, "Delete " + value, Toast.LENGTH_SHORT).show();

                    BackgroundWorker backgroundWorkerCategories = new BackgroundWorker(getApplicationContext());
                    backgroundWorkerCategories.execute("delete value", value, date, type, category);
//
//                    Intent i = new Intent(Item.this, DisplayData.class);
//                    finish();
                }
            }).create().show();
    }
}
