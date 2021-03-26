package com.example.softwarepatternsca2.RegisterLogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softwarepatternsca2.ObjectClasses.Customer;
import com.example.softwarepatternsca2.R;
import com.example.softwarepatternsca2.CustomerFeatures.WelcomeCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterCustomer extends AppCompatActivity {
    private static final String USER = "Customer";
    private static final String TAG = "RegisterCustomer";

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    String email;
    String uid;
    Customer customer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);
        mAuth = FirebaseAuth.getInstance();
    }

    public void createCustomer(View v) {
        EditText nameTxt = (EditText) findViewById(R.id.name);
        EditText addressTxt = (EditText) findViewById(R.id.address);
        EditText locationTxt = (EditText) findViewById(R.id.location);
        EditText numberTxt = (EditText) findViewById(R.id.number);
        EditText emailTxt = (EditText) findViewById(R.id.email);
        EditText passwordTxt = (EditText) findViewById(R.id.password);
        EditText passwordTxt2 = (EditText) findViewById(R.id.password2);


        email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String password2 = passwordTxt2.getText().toString();
        String address = addressTxt.getText().toString();
        String location = locationTxt.getText().toString();
        String number = numberTxt.getText().toString();
        String name = nameTxt.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)  || TextUtils.isEmpty(address) || TextUtils.isEmpty(location)
                || TextUtils.isEmpty(number) || TextUtils.isEmpty(name)|| TextUtils.isEmpty(name)|| TextUtils.isEmpty(password2)||!password.equals(password2)) {
            Toast.makeText(getApplicationContext(), "Please ensure all fields are completed and that passwords match",
                    Toast.LENGTH_SHORT).show();
            return;
        }else {

            customer = new Customer( name,  address,  location,  email,  number,
                    "customerId",  "cardNumber",  "securityCode",  "cardExpiry", false , 0);
            registerCustomer(email, password);

        }
    }

    public void registerCustomer(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"Create user with email : success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        }else{
                            Log.w(TAG,"Create user with email : failure",task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void updateUI(FirebaseUser currentUser){

        uid=currentUser.getUid();
        customer.setCustomerId(uid);
        mDatabase.child(uid).setValue(customer);

        Intent welcomeIntent = new Intent(this, WelcomeCustomer.class);
        startActivity(welcomeIntent);
    }

}