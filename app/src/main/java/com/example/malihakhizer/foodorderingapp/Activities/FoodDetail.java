package com.example.malihakhizer.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.malihakhizer.foodorderingapp.Data.OrderDbHelper;
import com.example.malihakhizer.foodorderingapp.Models.Food;
import com.example.malihakhizer.foodorderingapp.Models.Order;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    private static final String TAG = "MyTag";
    TextView foodName,foodPrice,foodDescription;
    ImageView foodImage;
    CollapsingToolbarLayout toolbarLayout;
    FloatingActionButton cartButton;
    ElegantNumberButton numberButton;

    String foodId = "";
    Food currentfood;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Foods");

        initViews();

        if(getIntent() != null) {
            foodId= getIntent().getStringExtra("foodId");
            Log.d(TAG, "FoodDetail --- onCreate: " + foodId);
        }

         getDetails();

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: adding into db");
                OrderDbHelper dbHelper = new OrderDbHelper(getApplicationContext());
                dbHelper.addToCart(new Order(
                                foodId,
                                currentfood.getName(),
                                numberButton.getNumber(),
                                currentfood.getPrice(),
                                currentfood.getDiscount(),
                                currentfood.getImage(),
                                currentfood.getDescription()
                ));

                Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void getDetails() {
        Log.d(TAG, "getDetails: called");

        reference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentfood = dataSnapshot.getValue(Food.class);
                Log.d(TAG, "onDataChange: "+currentfood.getName());
                Picasso.with(getApplicationContext()).load(currentfood.getImage()).into(foodImage);
                toolbarLayout.setTitle(currentfood.getName());
                foodPrice.setText(currentfood.getPrice());
                foodDescription.setText(currentfood.getDescription());
                foodName.setText(currentfood.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void initViews(){
        foodName = findViewById(R.id.food_name);
        foodPrice = findViewById(R.id.food_price);
        foodDescription = findViewById(R.id.food_description);
        foodImage = findViewById(R.id.food_image);
        toolbarLayout = findViewById(R.id.collapsing);
        cartButton = findViewById(R.id.button_cart);
        numberButton = findViewById(R.id.number_button);
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        toolbarLayout.setExpandedTitleColor(R.style.ExpandedAppBar);
    }
}
