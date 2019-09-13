package com.example.malihakhizer.foodorderingapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.malihakhizer.foodorderingapp.Adapters.MenuAdapter;
import com.example.malihakhizer.foodorderingapp.Models.Category;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MenuAdapter.OnItemListener {

    private static final String TAG = "MyTag";
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseAuth auth;

    TextView textView;
    RecyclerView recyclerView_menu;
    RecyclerView.LayoutManager layoutManager;
    MenuAdapter adapter ;
    ArrayList<Category> categories = new ArrayList<>();
    ArrayList<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("category");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set name for user
        String userName = auth.getCurrentUser().getDisplayName();
        View headerView = navigationView.getHeaderView(0);
        textView = findViewById(R.id.txtFullName);
      //  textView.setText(userName);

        recyclerView_menu = findViewById(R.id.category_recycler_view);
        recyclerView_menu.setHasFixedSize(true);
        recyclerView_menu.setLayoutManager(new LinearLayoutManager(this));

        //load menu
         loadmenu();
    }

    public void loadmenu(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                categories.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    keys.add(snapshot.getKey());
                    Category category = snapshot.getValue(Category.class);
                    Log.d(TAG, "onDataChange: "+ category.getName());
                    categories.add(category);
                }
                adapter = new MenuAdapter(getApplicationContext(), categories, new MenuAdapter.OnItemListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Toast.makeText(Home.this, "onClick: "+categories.get(position).getName()+" clicked!", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "onClick: category: "+categories.get(position).getName());
                        Log.d(TAG, "onClick: key: "+keys.get(position));

                        Intent intent = new Intent(getApplicationContext(),FoodList.class);
                        intent.putExtra("menuId",keys.get(position));
                        startActivity(intent);
                    }
                });
                recyclerView_menu.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.logout:
                auth.signOut();

                FirebaseUser user = auth.getCurrentUser();
                if(user == null){
                    Intent intent = new Intent(Home.this, Login.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Error Logging out", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_my_orders) {

        } else if (id == R.id.nav_my_profile) {

        } else if (id == R.id.nav_contact_us) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view, int position) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView categoryImage;
        TextView categoryName;
        MenuAdapter.OnItemListener listener;

        public ViewHolder(@NonNull View itemView,MenuAdapter.OnItemListener onItemListener) {
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

}
