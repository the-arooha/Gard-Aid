package com.example.loginactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Interests extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment=new HomeFragment();
    ChatFragment chatFragment=new ChatFragment();
    NotificationFragment notificationFragment=new NotificationFragment();
    ActivityFragment activityFragment=new ActivityFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interests);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

        BadgeDrawable badgeDrawable=bottomNavigationView.getOrCreateBadge(R.id.notification);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                    return true;
                case R.id.chat:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,chatFragment).commit();
                    return true;
                case R.id.notification:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                    return true;
                case R.id.activity:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,activityFragment).commit();
                    return true;
                case R.id.categories:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,activityFragment).commit();
                    return true;
                case R.id.problems:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,activityFragment).commit();
                    return true;
                case R.id.composting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,activityFragment).commit();
                    return true;


            }
            return false;
        });

    }
}