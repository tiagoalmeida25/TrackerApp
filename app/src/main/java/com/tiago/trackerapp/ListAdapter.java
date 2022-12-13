package com.tiago.trackerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.helper.widget.Layer;

import org.w3c.dom.Text;

public class ListAdapter extends ArrayAdapter<Item> {
    public ListAdapter(Context context, ArrayAdapter<Item> arrayAdapter){
        super(context, R.layout.list_item);
    }

    @NonNull
    @Override
    public getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Item item = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        TextView text = convertView.findViewById(R.id.text);
        TextView date = convertView.findViewById(R.id.textDate);

        text.setText(item.name);
        date.setText(item.date);

        return convertView;
    }
}


