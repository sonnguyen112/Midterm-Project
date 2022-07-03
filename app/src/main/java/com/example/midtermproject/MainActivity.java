package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.midtermproject.Adapters.ShopAdapter;
import com.example.midtermproject.Adapters.ShopListener;
import com.example.midtermproject.Models.Shop;
import com.example.midtermproject.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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
        ShopAdapter shopAdapter = new ShopAdapter(shopList, MainActivity.this, new ShopListener() {
            @Override
            public void onItemClick(Shop shop) {
                Intent intent = new Intent(MainActivity.this, ProductActivity.class);
                intent.putExtra("shopId", shop.getId());
                intent.putExtra("shopName", shop.getName());
                intent.putExtra("shopLocation", shop.getLocation());
                intent.putExtra("shopImg", shop.getImg());
                startActivity(intent);
            }
        });
        binding.ShopsRecyclerView.setAdapter(shopAdapter);
        binding.ShopsRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,3));

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
        Picasso.get().load("https://a.cdn-hotels.com/gdcs/production141/d778/6b200721-9661-4680-aca2-d6e33ce46cf0.jpg?impolicy=fcrop&w=1600&h=1066&q=medium").placeholder(R.drawable.default_image_shop).into(binding.BigImg);
        binding.mapStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, mapStoresActivity.class);
                startActivity(intent);
            }
        });
    }
}