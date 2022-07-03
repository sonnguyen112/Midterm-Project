package com.example.midtermproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Models.Good;
import com.example.midtermproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GoodAdapter extends RecyclerView.Adapter<GoodAdapter.ViewHolder> {

    ArrayList<Good> goodList;
    Context context;

    public GoodAdapter(ArrayList<Good> goodList, Context context) {
        this.goodList = goodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_good, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Good good = goodList.get(position);
        holder.titleEdt.setText(good.getName() + " x" + String.valueOf(good.getAmount()));
        holder.totalPriceEdt.setText("Total Price: " + String.valueOf(good.getAmount() * good.getPrice()) + "đ");
        Picasso.get().load(good.getGoodImg()).into(holder.goodImg);
    }

    @Override
    public int getItemCount() {
        return goodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleEdt, totalPriceEdt;
        ImageView goodImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleEdt = itemView.findViewById(R.id.title);
            totalPriceEdt = itemView.findViewById(R.id.totalPrice);
            goodImg = itemView.findViewById(R.id.goodImg);
        }
    }
}
