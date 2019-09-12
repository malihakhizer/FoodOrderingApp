package com.example.malihakhizer.foodorderingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malihakhizer.foodorderingapp.Models.User;
import com.example.malihakhizer.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Signup extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference reference;

    String name, email, password;
    String regex = "^[\\p{L}\\p{N}\\._%+-]+@[\\p{L}\\p{N}\\.\\-]+\\.[\\p{L}]{2,}$";

    Button signupButton;
    EditText editText_name, editText_email, editText_password;
    TextView textView_name,textView_email,textView_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);

        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users");
        auth = FirebaseAuth.getInstance();

        initViews();

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText_name.getText().toString().trim();
                email = editText_email.getText().toString().trim();
                password = editText_password.getText().toString().trim();
                validate();
            }
        });

    }
        public void initViews () {
            editText_name = findViewById(R.id.signup_edittext_name);
            editText_email = findViewById(R.id.signup_edittext_email);
            editText_password = findViewById(R.id.signup_edittext_password);

            textView_email = findViewById(R.id.email_validation);
            textView_password = findViewById(R.id.password_validation);
            textView_name =findViewById(R.id.name_validation);

            signupButton = findViewById(R.id.button_signup);
        }

        public void validate () {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(regex);

            if (email.matches("") && password.matches("")) {
                textView_email.setText("Email required");
                textView_password.setText("Password required");
            /*} else if (email.matches("")) {
                textView_email.setText("Email required");
                textView_password.setText(" ");
            } else if (password.matches("")) {
                textView_password.setText("Password required");
                textView_email.setText(" ");
                // Toast.makeText(this, "Email and Password are required", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6 || password.length() > 20) {
                textView_password.setText("Password between 6 and 20 alphanumeric characters");
                //   Toast.makeText(this, "Password between 6 and 20 alphanumeric characters", Toast.LENGTH_SHORT).show();

            } else if (matcher.matches() == false) {
                textView_email.setText("Incorrect Email");*/
            } else {
                Toast.makeText(this, "Signup Successful", Toast.LENGTH_SHORT).show();
                signupUser();
            }
        }

        public void signupUser () {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Signup.this, "Successfully signed up!", Toast.LENGTH_SHORT).show();
                                FirebaseUser fuser = auth.getCurrentUser();
                                String key = fuser.getUid();
                                User user = new User(name,email,password,key,"");
                                reference.child(key).setValue(user);
                                SUpdateUI();
                            } else {
                                Toast.makeText(Signup.this, "Error creating a new user!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        public void SUpdateUI(){

                // and since user has been logged in so we can access the current user from any activity
                FirebaseUser user = auth.getCurrentUser();

                if (user == null) {
                    // Toast.makeText(this, "User not Logged in", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    auth.signOut();
                    Intent intent = new Intent(this, Login.class);
                    startActivity(intent);
                }

        }
    }
