package com.example.loginactivity;

import static com.example.loginactivity.R.id.profileImage;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginactivity.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {


    private CircleImageView profileImage;
    private EditText mName,mUsername,mEmail,mPassword,mConfirmPassword,mgender,mstatus,mdob,mqualification,mstate;
    private Button mRegister;
    private TextView mAlreadyuser;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private ProgressDialog loader;
    private String onlineUserID="";
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        mName = findViewById(R.id.inputName2);
        mUsername = findViewById(R.id.inputUsername2);
        mEmail = findViewById(R.id.inputemail2);
        mPassword = findViewById(R.id.inputpassword2);
        mConfirmPassword = findViewById(R.id.inputconfirmpassword2);
        mgender = findViewById(R.id.inputgender);
        mstatus = findViewById(R.id.inputstatus);
        mdob = findViewById(R.id.inputdob);
        mqualification = findViewById(R.id.inputqualification);
        mstate = findViewById(R.id.inputstate);
        mRegister = findViewById(R.id.signupforanewaccount);
        mAlreadyuser = findViewById(R.id.alreadyhaveanaccount);
        profileImage=findViewById(R.id.profileImage);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        mAlreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username=mUsername.getText().toString();
                String email =mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String confirmpassword=mConfirmPassword.getText().toString().trim();
                String name=mName.getText().toString();
                String gender=mgender.getText().toString();
                String status=mstatus.getText().toString();
                String dob=mdob.getText().toString();
                String qualification=mqualification.getText().toString();
                String state=mstate.getText().toString();

                if(TextUtils.isEmpty(username)){
                    mUsername.setError("Username is Required");
                    return;
                }

                if(TextUtils.isEmpty(gender)){
                    mgender.setError("Gender is Required");
                    return;
                }

                if(TextUtils.isEmpty(status)){
                    mstatus.setError("Status is Required");
                    return;
                }

                if(TextUtils.isEmpty(dob)){
                    mdob.setError("DOB is Required");
                    return;
                }

                if(TextUtils.isEmpty(qualification)){
                    mqualification.setError("Qualification is Required");
                    return;
                }

                if(TextUtils.isEmpty(state)){
                    mstate.setError("State is Required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }

                if(TextUtils.isEmpty(confirmpassword)){
                    mConfirmPassword.setError("Re-enter the password");
                    return;
                }

                if(password.length()<6){
                    mPassword.setError("Password must be >= 6 characters to become strong");
                    return;
                }

                if(!confirmpassword.equals(password)){
                    mConfirmPassword.setError("Password not matching");
                    return;
                }

                if(resultUri==null){
                    Toast.makeText(Registration.this, "Profile Image is required", Toast.LENGTH_SHORT).show();
                }

                else{
                    loader.setMessage("Registration is in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Registration.this, "Registration failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }else{
                                onlineUserID=mAuth.getCurrentUser().getUid();
                                reference =FirebaseDatabase.getInstance().getReference().child("users").child(onlineUserID);
                                Map hashMap=new HashMap();
                                hashMap.put("Id",onlineUserID);
                                hashMap.put("Name",name);
                                hashMap.put("Username",username);
                                hashMap.put("Email",email);
                                hashMap.put("Password",password);
                                hashMap.put("Gender",gender);
                                hashMap.put("Status",status);
                                hashMap.put("DOB",dob);
                                hashMap.put("Qualification",qualification);
                                hashMap.put("State",state);


                                reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                      if(task.isSuccessful()){
                                          Toast.makeText(Registration.this, "Details set Successfully", Toast.LENGTH_SHORT).show();
                                      }else{
                                          Toast.makeText(Registration.this, "Failed to upload data" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                                      }

                                      finish();
                                      loader.dismiss();
                                    }
                                });

                                final StorageReference filePath= FirebaseStorage.getInstance().getReference().child("profile images").child(onlineUserID);
                                Bitmap bitmap=null;
                                try {
                                  bitmap= MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),resultUri);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
                                byte[] data=byteArrayOutputStream.toByteArray();
                                UploadTask uploadTask=filePath.putBytes(data);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if(taskSnapshot.getMetadata().getReference() !=null){
                                            Task<Uri> result=taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imageUrl=uri.toString();
                                                    Map hashMap=new HashMap();
                                                    hashMap.put("profileimageurl",imageUrl);
                                                    reference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(Registration.this, "Profile Image added successfully", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                Toast.makeText(Registration.this, "Process failed" +task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });

                                                    finish();
                                                }
                                            });
                                        }
                                    }
                                });

                                Intent intent=new Intent(Registration.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null){
            resultUri=data.getData();
            profileImage.setImageURI(resultUri);
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


}