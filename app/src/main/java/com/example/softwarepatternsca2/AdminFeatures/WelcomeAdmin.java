package com.example.softwarepatternsca2.AdminFeatures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.softwarepatternsca2.R;

public class WelcomeAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_admin2);
    }

    public void createItem(View view) {
        Intent intent = new Intent(getApplicationContext(),AddStockItem.class);
        startActivity(intent);
    }

    public void viewAllItems(View view) {
        Intent intent = new Intent(getApplicationContext(),ViewStockedItems.class);
        startActivity(intent);
    }
}