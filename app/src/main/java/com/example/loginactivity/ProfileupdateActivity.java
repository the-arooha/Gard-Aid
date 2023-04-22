package com.example.loginactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileupdateActivity extends AppCompatActivity {
       private EditText nametext, usernametext, passwordtext, gendertext, statustext, dobtext, qualitext, statetext;
        private ImageView imageView2;
        private FirebaseUser user;
        private DatabaseReference reference;
        private String userID;

        private static final int PICK_IMAGE = 1;
        private Uri imageUri;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profileupdate);

            nametext = findViewById(R.id.nametext);
            usernametext = findViewById(R.id.usernametext);
            passwordtext = findViewById(R.id.passwordtext);
            gendertext = findViewById(R.id.gendertext);
            statustext = findViewById(R.id.statustext);
            dobtext = findViewById(R.id.dobtext);
            qualitext = findViewById(R.id.qualitext);
            statetext = findViewById(R.id.statetext);
            imageView2 = findViewById(R.id.imageView2);

            user = FirebaseAuth.getInstance().getCurrentUser();
            reference = FirebaseDatabase.getInstance().getReference("users");
            userID = user.getUid();


            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });

            reference.child(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("Name").getValue().toString();
                    String username = dataSnapshot.child("Username").getValue().toString();
                    String password = dataSnapshot.child("Password").getValue().toString();
                    String gender = dataSnapshot.child("Gender").getValue().toString();
                    String status = dataSnapshot.child("Status").getValue().toString();
                    String dob = dataSnapshot.child("DOB").getValue().toString();
                    String qualification = dataSnapshot.child("Qualification").getValue().toString();
                    String state = dataSnapshot.child("State").getValue().toString();
                    String profileImageUrl = dataSnapshot.child("profileimageurl").getValue().toString();

                    nametext.setText(name);
                    usernametext.setText(username);
                    passwordtext.setText(password);
                    gendertext.setText(gender);
                    statustext.setText(status);
                    dobtext.setText(dob);
                    qualitext.setText(qualification);
                    statetext.setText(state);
                    Glide.with(getApplicationContext()).load(profileImageUrl).into(imageView2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileupdateActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });
        }

        private void openFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                imageView2.setImageURI(imageUri);
                uploadImageToFirebaseStorage();
            }
        }

        private void uploadImageToFirebaseStorage() {
            final StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + userID + ".jpg");
            if (imageUri != null) {
                profileImageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        reference.child(userID).child("profileimageurl").setValue(uri.toString());
                                        Toast.makeText(ProfileupdateActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileupdateActivity.this, "Failed to upload image.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        public void UPDATE(View view) {
            String name = nametext.getText().toString();
            String username = usernametext.getText().toString();
            String password = passwordtext.getText().toString();
            String gender = gendertext.getText().toString();
            String status = statustext.getText().toString();
            String dob = dobtext.getText().toString();
            String qualification = qualitext.getText().toString();
            String state = statetext.getText().toString();

            if (TextUtils.isEmpty(name)) {
                nametext.setError("Name is required!");
                return;
            }

            if (TextUtils.isEmpty(username)) {
                usernametext.setError("Username is required!");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordtext.setError("Password is required!");
                return;
            }

            if (TextUtils.isEmpty(gender)) {
                gendertext.setError("Gender is required!");
                return;
            }

            if (TextUtils.isEmpty(status)) {
                statustext.setError("Status is required!");
                return;
            }

            if (TextUtils.isEmpty(dob)) {
                dobtext.setError("Date of birth is required!");
                return;
            }

            if (TextUtils.isEmpty(qualification)) {
                qualitext.setError("Qualification is required!");
                return;
            }

            if (TextUtils.isEmpty(state)) {
                statetext.setError("State is required!");
                return;
            }

            reference.child(userID).child("Name").setValue(name);
            reference.child(userID).child("Username").setValue(username);
            reference.child(userID).child("Password").setValue(password);
            reference.child(userID).child("Gender").setValue(gender);
            reference.child(userID).child("Status").setValue(status);
            reference.child(userID).child("DOB").setValue(dob);
            reference.child(userID).child("Qualification").setValue(qualification);
            reference.child(userID).child("State").setValue(state);

            Toast.makeText(ProfileupdateActivity.this, "Profile updated successfully!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
