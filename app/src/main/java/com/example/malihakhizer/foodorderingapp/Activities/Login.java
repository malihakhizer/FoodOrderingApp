package com.example.malihakhizer.foodorderingapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.malihakhizer.foodorderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference reference;

    TextView createAccount;
    Button loginButton;
    EditText editText_email;
    EditText editText_password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("users");

        initView();


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  progressDialog = new ProgressDialog(getApplicationContext());
              //  progressDialog.setMessage("Please wait...");
              //  progressDialog.show();
                login();
            }
        });

    }

    public void initView() {
        createAccount = findViewById(R.id.create_account);
        loginButton = findViewById(R.id.buttonLogin);
        editText_email = findViewById(R.id.login_edittext_email);
        editText_password = findViewById(R.id.login_edittext_password);
    }

    public void login() {
        String email = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           // progressDialog.dismiss();
                            Toast.makeText(Login.this, "User logged in!", Toast.LENGTH_SHORT).show();
                            updateUI();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Error logging in!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void updateUI() {
        // and since user has been logged in so we can access the current user from any activity
        FirebaseUser user = auth.getCurrentUser();

        if (user == null) {
            //  Toast.makeText(this, "User not Logged in", Toast.LENGTH_SHORT).show();
            return;
        } else {

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }
}
