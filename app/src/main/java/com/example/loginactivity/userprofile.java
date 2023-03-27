package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class userprofile extends AppCompatActivity {

    private TextView textViewFullName,textViewemailid,textViewgender,textViewstatus,textViewdob,textViewqualification,txtViewstate;
    private String fullName,email,gender,ststus,dob,qualification,state;
    private ImageView imageview;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        textViewFullName= findViewById(R.id.gendertext);
    }


    //logout from the application
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}