package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.midtermproject.Adapters.GoodAdapter;
import com.example.midtermproject.Models.Good;
import com.example.midtermproject.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements PhoneNumDialog.PhoneNumDialogListener {
    ActivityCartBinding binding;
    int totalPrice = 0;
    String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        shopId = getIntent().getStringExtra("shopId");
        ArrayList<Good> goodList = GoodArrayList.goodList;

        for (int i = 0; i < goodList.size(); i++){
            totalPrice += goodList.get(i).getPrice() * goodList.get(i).getAmount();
        }
        binding.Total.setText("TOTAL: " + String.valueOf(totalPrice) + "Đ");
        GoodAdapter goodAdapter = new GoodAdapter(goodList, CartActivity.this);
        binding.GoodsRecyclerView.setAdapter(goodAdapter);
        binding.GoodsRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        binding.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalPrice > 0){
                    numberphoneDialog();
                }
            }
        });
        binding.cancelCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MainActivity.class);
                intent.putExtra("shopId", shopId);
                startActivity(intent);
            }
        });
    }

    private void numberphoneDialog() {
        PhoneNumDialog phoneNumDialog = new PhoneNumDialog();
        phoneNumDialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void thankYou() {
        Intent intent = new Intent(CartActivity.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Thank You Very Much", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ProductActivity.class);
        intent.putExtra("shopId", shopId);
        startActivity(intent);
    }
}