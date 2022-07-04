package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.midtermproject.Adapters.ProductAdapter;
import com.example.midtermproject.Models.Good;
import com.example.midtermproject.Models.Product;
import com.example.midtermproject.databinding.ActivityProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity{

    ActivityProductBinding binding;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database =FirebaseDatabase.getInstance();
        ArrayList<Integer> countProduct = new ArrayList<>();
        ArrayList<Product> productList = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(productList, ProductActivity.this, new ProductAdapter.ProductListener() {
            @Override
            public void updateCountProduct(String act, int indexProduct) {
                if (act.equals("increase")){
                    countProduct.set(indexProduct, countProduct.get(indexProduct) + 1);
                    binding.put.setEnabled(true);
                }
                else if(act.equals("decrease")){
                    countProduct.set(indexProduct, countProduct.get(indexProduct) - 1);
                    for (int i = 0; i < countProduct.size(); i++){
                        if (countProduct.get(i) != 0){
                            return;
                        }
                    }
                    binding.put.setEnabled(false);
                }
            }
        });
        binding.ProductssRecyclerView.setAdapter(productAdapter);
        binding.ProductssRecyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));

        String shopId = getIntent().getStringExtra("shopId");
        String shopName = getIntent().getStringExtra("shopName");
        String shopLocation = getIntent().getStringExtra("shopLocation");
        String shopImg = getIntent().getStringExtra("shopImg");

        Picasso.get().load(shopImg).into(binding.BigImg);

        binding.shopName.setText(shopName);
        binding.shopLocation.setText(shopLocation);
        binding.shopLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri=Uri.parse("geo:0,0?q="+Uri.encode(shopLocation));
                Intent intent= new Intent(Intent.ACTION_VIEW,gmmIntentUri );
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        database.getReference().child("Products").child(shopId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                countProduct.clear();
                Integer index = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    productList.add(product);
                    countProduct.add(0);
                    index = index + 1;
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Good> goodList = new ArrayList<>();
                for (int i = 0; i < productAdapter.getItemCount(); i++){
                    if (countProduct.get(i) > 0){
                        Good good = new Good(productList.get(i).getName(), countProduct.get(i), productList.get(i).getPrice());
                        good.setGoodImg(productList.get(i).getImg());
                        goodList.add(good);
                    }
                }
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                GoodArrayList.goodList = goodList;
                intent.putExtra("shopId", shopId);
                intent.putExtra("shopImg",shopImg);
                intent.putExtra("shopName",shopName);
                intent.putExtra("shopLocation",shopLocation);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}