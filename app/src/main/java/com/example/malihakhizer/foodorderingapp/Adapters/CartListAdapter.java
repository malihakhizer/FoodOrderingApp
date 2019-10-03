package com.example.malihakhizer.foodorderingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malihakhizer.foodorderingapp.Models.Order;
import com.example.malihakhizer.foodorderingapp.OnItemListener;
import com.example.malihakhizer.foodorderingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartViewHolder> {
    ArrayList<Order> orders;
    OnItemListener itemListener;
    Context ctx;
    View rootView;

    public CartListAdapter( Context ctx,ArrayList<Order> ordder,OnItemListener itemListener) {
        this.itemListener = itemListener;
        this.ctx = ctx;
        this.orders=ordder;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(rootView,itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.foodName.setText(orders.get(position).getProductName());
        holder.foodPrice.setText("Rs. " + orders.get(position).getPrice());
        holder.foodDescription.setText(orders.get(position).getDescription());
        holder.foodQuantity.setText(orders.get(position).getQuantity());
        String image_url = orders.get(position).getPictureUrl();

        if (!image_url.equals("")) {
            Picasso.with(ctx).load(image_url).into(holder.foodPictureUrl);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }





    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemListener listener;
        TextView foodName;
        TextView foodPrice;
        TextView foodQuantity;
        ImageView foodPictureUrl;
        TextView foodDescription;

        public CartViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            this.listener = onItemListener;
            this.foodName = itemView.findViewById(R.id.cart_food_name);
            this.foodPrice = itemView.findViewById(R.id.cart_food_price);
            this.foodQuantity = itemView.findViewById(R.id.cart_food_quantity);
            this.foodPictureUrl = itemView.findViewById(R.id.cart_food_image);
            this.foodDescription = itemView.findViewById(R.id.cart_food_des);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

}
