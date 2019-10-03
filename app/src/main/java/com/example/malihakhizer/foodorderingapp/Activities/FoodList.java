package com.example.malihakhizer.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.malihakhizer.foodorderingapp.Adapters.FoodlistAdapter;
import com.example.malihakhizer.foodorderingapp.Models.Food;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity implements FoodlistAdapter.OnItemListener {
    private static final String TAG = "MyTag" ;
    FirebaseDatabase db;
    FirebaseAuth auth;
    DatabaseReference reference;

    RecyclerView recyclerView;
    FoodlistAdapter adapter;
    String categoryId;
    ArrayList<Food> foodlist = new ArrayList<>();
    ArrayList<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Foods");

        recyclerView = findViewById(R.id.food_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryId = getIntent().getStringExtra("menuId");
        Log.d(TAG, "Foodlist ---- onCreate: "+categoryId);
        getData();

    }

    public void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodlist.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                  //  Log.d(TAG, "onDataChange: ids: "+snapshot.getKey());
                    Food food = snapshot.getValue(Food.class);
                    Log.d(TAG, "onDataChange: foodname: "+food.getName()+" | foodid: "+snapshot.getKey());
                    if(food.getMenuId().equals(categoryId) ){
                       // Log.d(TAG, "if equal: "+food.getMenuId());
                        keys.add(snapshot.getKey());
                        foodlist.add(food);

                    }

                }

                adapter = new FoodlistAdapter(getApplicationContext(), foodlist, new FoodlistAdapter.OnItemListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Intent intent = new Intent(FoodList.this,FoodDetail.class);
                        intent.putExtra("foodId",keys.get(position));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view, int position) {

    }
}
