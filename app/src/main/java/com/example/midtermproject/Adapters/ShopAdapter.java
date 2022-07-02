package com.example.midtermproject.Adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Models.Shop;
import com.example.midtermproject.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    ArrayList<Shop> shopList;
    Context context;
    FirebaseDatabase database;

    public ShopAdapter(ArrayList<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_shops, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shop shop = shopList.get(position);
        Picasso.get().load(shop.getImg()).placeholder(R.drawable.default_image_shop).into(holder.shopImage);
        holder.shopName.setText(shop.getName());
        holder.shopLocation.setText(shop.getLocation());
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView shopImage;
        TextView shopName, shopLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            shopImage = itemView.findViewById(R.id.shopImage);
            shopName = itemView.findViewById(R.id.shopName);
            shopLocation = itemView.findViewById(R.id.shopLocation);
        }
    }
}
