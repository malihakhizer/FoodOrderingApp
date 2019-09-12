package com.example.malihakhizer.foodorderingapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malihakhizer.foodorderingapp.Models.Category;
import com.example.malihakhizer.foodorderingapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    View rootView;
    Context ctx;
    OnItemListener itemListener;
    ArrayList<Category> categories;

    public MenuAdapter(Context ctx, ArrayList<Category> categories,OnItemListener listener) {
        this.ctx = ctx;
        this.categories = categories;
        this.itemListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_list_item, parent, false);
        return new ViewHolder(rootView,itemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryName.setText(categories.get(position).getName());
        String image_url = categories.get(position).getUrl();

        if (!image_url.equals("")) {
            Picasso.with(ctx).load(image_url).into(holder.categoryImage);
        }


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImage;
        TextView categoryName;
        OnItemListener listener;

        public ViewHolder(@NonNull View itemView,OnItemListener onItemListener) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.category_image);
            categoryName = itemView.findViewById(R.id.category_name);
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
