//package com.tiago.trackerapp;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class ItemActivity extends AppCompatActivity {
//    ActivityUserBinding binding;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityUserBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        Intent intent = this.getIntent();
//
//        if(intent != null){
//            String name = intent.getStringExtra("name");
//            String date = intent.getStringExtra("date");
//            String type = intent.getStringExtra("type");
//            String category = intent.getStringExtra("category");
//
//            binding.textItem.setText(name);
//            binding.textItemDate.setText(date);
//            binding.textItemType.setText(type);
//            binding.textItemCategory.setText(category);
//
//        }
//
//    }
//}
