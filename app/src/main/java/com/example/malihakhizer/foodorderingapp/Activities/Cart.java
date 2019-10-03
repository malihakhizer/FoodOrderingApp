package com.example.malihakhizer.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malihakhizer.foodorderingapp.Adapters.CartListAdapter;
import com.example.malihakhizer.foodorderingapp.Data.OrderDbHelper;
import com.example.malihakhizer.foodorderingapp.Models.Order;
import com.example.malihakhizer.foodorderingapp.Models.Request;
import com.example.malihakhizer.foodorderingapp.OnItemListener;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    private static final String TAG ="MyTag" ;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartListAdapter adapter;

    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth ;

    TextView totalPrice;
    String address;

    Button checkout;
    ArrayList<Order> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* OrderDbHelper dbHelper = new OrderDbHelper(getApplicationContext());
        dbHelper.cleanCart();*/

        //firebase
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Requests");

        init();
        loadList();

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAlertDialog();
            }
        });

    }

    private void showAlertDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step!");
        alertDialog.setMessage("Enter your address");
        System.out.println("email address ");
        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request = new Request(auth.getCurrentUser().getPhoneNumber(),
                        auth.getCurrentUser().getDisplayName(),
                        edtAddress.getText().toString(),
                        totalPrice.getText().toString(),
                        orders
                );

                reference.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);
                new OrderDbHelper(getApplicationContext()).cleanCart();
                Toast.makeText(Cart.this, "Placed order successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }

    private void loadList() {

        orders = new OrderDbHelper(this).getCarts();
        adapter = new CartListAdapter(this, orders, new OnItemListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(TAG, "onClick: yes");
            }
        });

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setAdapter(adapter);

        int total = 0;
        for(Order order:orders){
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        }
        totalPrice.setText(String.valueOf(total));
    }

    private void init() {


        recyclerView = findViewById(R.id.recycler_view_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        totalPrice = findViewById(R.id.total_amount);
        checkout = findViewById(R.id.button_placeOrder);
    }

}
