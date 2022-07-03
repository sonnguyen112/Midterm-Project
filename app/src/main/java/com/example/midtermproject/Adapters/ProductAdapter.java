package com.example.midtermproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midtermproject.Models.Product;
import com.example.midtermproject.Models.Shop;
import com.example.midtermproject.R;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    ArrayList<Product> productList;
    Context context;
    ProductListener productListener;

    public ProductAdapter(ArrayList<Product> productList, Context context, ProductListener listener) {
        this.productList = productList;
        this.context = context;
        this.productListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        Picasso.get().load(product.getImg()).placeholder(R.drawable.default_image_shop).into(holder.productImage);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("Price: " + String.valueOf(product.getPrice()) + "đ");

        int index = position;
        holder.increase_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.amount.setText(String.valueOf(Integer.valueOf(holder.amount.getText().toString())+1));
                productListener.updateCountProduct("increase", index);
            }
        });

        holder.decrease_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.valueOf(holder.amount.getText().toString()) > 0){
                    holder.amount.setText(String.valueOf(Integer.valueOf(holder.amount.getText().toString())-1));
                    productListener.updateCountProduct("decrease", index);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName, productPrice, amount;
        Button increase_amount, decrease_amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            amount = itemView.findViewById(R.id.amount);
            increase_amount = itemView.findViewById(R.id.increase_amount);
            decrease_amount = itemView.findViewById(R.id.decrease_amount);
        }
    }

    public interface ProductListener{
        void updateCountProduct(String act, int indexProduct);
    }
}
