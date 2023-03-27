package com.example.loginactivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.loginactivity.Model.StoreModel;


public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);


        StoreModel storeModel = getIntent().getParcelableExtra("StoreModel");
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle(storeModel.getName());
        //actionBar.setSubtitle(storeModel.getAddress());
        //actionBar.setDisplayHomeAsUpEnabled(false);


        TextView buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}