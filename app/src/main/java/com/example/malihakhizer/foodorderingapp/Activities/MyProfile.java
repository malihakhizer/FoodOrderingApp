package com.example.malihakhizer.foodorderingapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malihakhizer.foodorderingapp.Models.User;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MyProfile extends AppCompatActivity {

    private static final String TAG = "MyTag";
    FirebaseAuth auth;
    DatabaseReference reference;
    TextView nametv;
    EditText emailtv, addresstv, numbertv;
    CircleImageView imageView;
    ImageButton button;
    FirebaseUser user;
    String url;
    String password;
    ImageView back;
    private PublishSubject<String> subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        reference = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String email = user.getEmail();
        Log.d(TAG, "onCreate: "+email);
        initViews();
        getUser(email);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: imgbutton");
                editProfile();
            }
        });

        subject = PublishSubject.create();
        subject.debounce(5000, TimeUnit.MILLISECONDS)
                .onBackpressureLatest()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        // Whatever processing on user String

                        Log.d(TAG, "onTextChanged: ");
                        String name = nametv.getText().toString().trim();
                        String id = auth.getCurrentUser().getUid();
                        String email = emailtv.getText().toString().trim();
                        String phone = numbertv.getText().toString().trim();
                        String address = addresstv.getText().toString().trim();

                        User u = new User(name,email,password,id,url,phone,address);

                        Log.d(TAG, "onTextChanged: " +user.getUid());
                        reference.child(user.getUid()).setValue(u);
                        Log.d(TAG, u.toString());
                    }
                });

    }

    private void initViews() {
        back = findViewById(R.id.profile_back);
        nametv = findViewById(R.id.user_name);
        emailtv = findViewById(R.id.user_email);
        addresstv = findViewById(R.id.user_address);
        numbertv = findViewById(R.id.user_number);
        imageView = findViewById(R.id.user_image);
        button = findViewById(R.id.edit);
    }

    public void editProfile() {

        if (!(emailtv.isEnabled() && addresstv.isEnabled() && numbertv.isEnabled())) {
            button.setImageResource(R.drawable.ic_done_black_24dp);
            emailtv.setEnabled(true);
            emailtv.setFocusableInTouchMode(true);
            emailtv.setCursorVisible(true);

            addresstv.setEnabled(true);
            addresstv.setFocusableInTouchMode(true);
            addresstv.setCursorVisible(true);

            numbertv.setEnabled(true);
            numbertv.setFocusableInTouchMode(true);
            numbertv.setCursorVisible(true);

            TextWatcher tw = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    Log.d(TAG, "beforeTextChanged: ");
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    subject.onNext(s.toString());

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.d(TAG, "afterTextChanged: ");

                }
            };

            emailtv.addTextChangedListener(tw);
            addresstv.addTextChangedListener(tw);
            numbertv.addTextChangedListener(tw);


        } else {
            button.setImageResource(R.drawable.ic_edit_black_24dp);
            emailtv.setEnabled(false);
            emailtv.setFocusableInTouchMode(false);
            emailtv.setCursorVisible(false);

            addresstv.setEnabled(false);
            addresstv.setFocusableInTouchMode(false);
            addresstv.setCursorVisible(false);

            numbertv.setEnabled(false);
            numbertv.setFocusableInTouchMode(false);
            numbertv.setCursorVisible(false);
        }
    }



    private void setOnUi(User fuser) {
        nametv.setText(fuser.getName());
        emailtv.setText(fuser.getEmail());
        addresstv.setText(fuser.getAddress());
        numbertv.setText(fuser.getPhone_number());
        if (fuser.getUrl().equals("")) {
            Picasso.with(getApplicationContext()).load("https://icon-library.net/images/no-profile-pic-icon/no-profile-pic-icon-5.jpg").into(imageView);
        } else {
            Picasso.with(getApplicationContext()).load(fuser.getUrl()).into(imageView);
        }

    }

    private void getUser(final String email) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //  Log.d(TAG, "onDataChange: ids: "+snapshot.getKey());
                    User user = snapshot.getValue(User.class);
                    Log.d(TAG, "onDataChange: username: " + user.getName() + " | userid: " + snapshot.getKey());


                    if (user.getEmail().equals(email)) {
                        Log.d(TAG, "onDataChange: ok");
                        url = user.getUrl();
                        password = user.getPassword();
                        setOnUi(user);
                    } else {
                        Log.d(TAG, "onDataChange: not ok");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
