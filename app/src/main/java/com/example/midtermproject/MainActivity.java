package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.midtermproject.Adapters.ShopAdapter;
import com.example.midtermproject.Models.Shop;
import com.example.midtermproject.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();

        ArrayList<Shop> shopList = new ArrayList<>();
        ShopAdapter shopAdapter = new ShopAdapter(shopList, MainActivity.this);
        binding.ShopsRecyclerView.setAdapter(shopAdapter);
        binding.ShopsRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        database.getReference().child("Shops").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Shop shop = dataSnapshot.getValue(Shop.class);
                    shopList.add(shop);
                }
                shopAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}