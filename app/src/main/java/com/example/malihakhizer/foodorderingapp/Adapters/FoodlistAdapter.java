package com.example.malihakhizer.foodorderingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malihakhizer.foodorderingapp.Models.Category;
import com.example.malihakhizer.foodorderingapp.Models.Food;
import com.example.malihakhizer.foodorderingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FoodlistAdapter extends RecyclerView.Adapter<FoodlistAdapter.ViewHolder> {
        View rootView;
        Context ctx;
        OnItemListener itemListener;
        ArrayList<Food> foodlist;


        public FoodlistAdapter(Context ctx, ArrayList<Food> foodlist2, FoodlistAdapter.OnItemListener listener) {
        this.ctx = ctx;
        this.foodlist = foodlist2;
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public FoodlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.foodlist_item, parent, false);
        return new FoodlistAdapter.ViewHolder(rootView,itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.foodName.setText(foodlist.get(position).getName());
        String image_url = foodlist.get(position).getImage();

        if (!image_url.equals("")) {
            Picasso.with(ctx).load(image_url).into(holder.foodImg);
        }
    }

    @Override
    public int getItemCount() {
        return foodlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView foodImg;
        TextView foodName;
        FoodlistAdapter.OnItemListener listener;

        public ViewHolder(@NonNull View itemView,FoodlistAdapter.OnItemListener onItemListener) {
            super(itemView);
            foodImg = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
            this.listener = onItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }

    public interface OnItemListener{
        public void onClick(View view, int position);
    }
}
