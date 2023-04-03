package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class userprofile extends AppCompatActivity {

    private TextView textViewFullName,textViewusername,textViewemailid,textViewgender,textViewstatus,textViewpassword,textViewdob,textViewqualification,textViewstate;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private String fullName,email,gender,status,dob,qualification,state,username,password;
    private ImageView imageview;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        textViewFullName= findViewById(R.id.textView3);
        textViewpassword=findViewById(R.id.passwordtext);
        textViewusername=findViewById(R.id.username);
        textViewemailid=findViewById(R.id.textView4);
        textViewgender= findViewById(R.id.gendertext);
        textViewstatus=findViewById(R.id.statustext);
        textViewdob= findViewById(R.id.dobtext);
        textViewqualification=findViewById(R.id.qualitext);
        textViewstate= findViewById(R.id.statetext);
        imageview=findViewById(R.id.imageView2);
        progressBar=findViewById(R.id.progressBar);

        authProfile= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= authProfile.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(userprofile.this, "Something went wrong! User's details are not available at the moment.",
                    Toast.LENGTH_LONG).show();
        }else{
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID= firebaseUser.getUid();

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readUserDetails= snapshot.getValue(ReadWriteUserDetails.class);
                if(readUserDetails != null){
                    fullName=readUserDetails.Name;
                    email=firebaseUser.getEmail();
                    username=readUserDetails.Username;
                    gender=readUserDetails.Gender;
                    status=readUserDetails.Status;
                    dob=readUserDetails.DOB;
                    qualification=readUserDetails.Qualification;
                    state=readUserDetails.State;
                    password=readUserDetails.Password;

                    textViewpassword.setText(password);
                    textViewFullName.setText(fullName);
                    textViewusername.setText(username);
                    textViewemailid.setText(email);
                    textViewgender.setText(gender);
                    textViewstatus.setText(status);
                    textViewdob.setText(dob);
                    textViewqualification.setText(qualification);
                    textViewstate.setText(state);

                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(userprofile.this, "Something went wrong!",Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    public void delete(View view){
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("We are deleting your Account");
        progressDialog.setCancelable(false);
        progressDialog.show();
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(null)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void avoid) {
                      FirebaseAuth.getInstance().getCurrentUser().delete()
                              .addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful()){
                                          progressDialog.dismiss();
                                          Intent intent=new Intent(userprofile.this,Registration.class);
                                          startActivity(intent);
                                      }else{

                                      }
                                  }
                              });
                    }
                });
    }
    //logout from the application
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}