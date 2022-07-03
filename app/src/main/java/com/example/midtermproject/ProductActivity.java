package com.example.midtermproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.midtermproject.Adapters.ProductAdapter;
import com.example.midtermproject.Models.Product;
import com.example.midtermproject.databinding.ActivityProductBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityProductBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database =FirebaseDatabase.getInstance();
        ArrayList<Product> productList = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(productList, ProductActivity.this);
        binding.ProductssRecyclerView.setAdapter(productAdapter);
        binding.ProductssRecyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));

        String shopId = getIntent().getStringExtra("shopId");
        String shopName = getIntent().getStringExtra("shopName");
        String shopLocation = getIntent().getStringExtra("shopLocation");
        String shopImg = getIntent().getStringExtra("shopImg");
        Picasso.get().load(shopImg).placeholder(R.drawable.default_image_shop).into(binding.BigImg);


        binding.shopName.setText(shopName);
        binding.shopLocation.setText(shopLocation);

        database.getReference().child("Products").child(shopId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.putToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < productAdapter.getItemCount(); i++){
                    View v = binding.ProductssRecyclerView.getLayoutManager().findViewByPosition(i);
                    TextView amount = v.findViewById(R.id.amount);
                    Toast.makeText(ProductActivity.this, amount.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                numberphoneDialog();
            }
        });
    }

    private void numberphoneDialog() {
        PhoneNumDialog phoneNumDialog = new PhoneNumDialog();
        phoneNumDialog.show(getSupportFragmentManager(), "Dialog");
    }
}