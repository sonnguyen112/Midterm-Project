package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.midtermproject.Adapters.GoodAdapter;
import com.example.midtermproject.Models.Good;
import com.example.midtermproject.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity{
    ActivityCartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();


        ArrayList<Good> goodList = GoodArrayList.goodList;
        int totalPrice = 0;
        for (int i = 0; i < goodList.size(); i++){
            totalPrice += goodList.get(i).getPrice() * goodList.get(i).getAmount();
        }
        binding.Total.setText("TOTAL: " + String.valueOf(totalPrice));
        GoodAdapter goodAdapter = new GoodAdapter(goodList, CartActivity.this);
        binding.GoodsRecyclerView.setAdapter(goodAdapter);
        binding.GoodsRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
    }
}