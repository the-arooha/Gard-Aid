package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.loginactivity.Model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Date;

public class AskAQuestionActivity extends AppCompatActivity {

        private Spinner spinner;
        private EditText questionEditText;
        private ImageView questionImageView;
        private Uri imageUri = null;
        private DatabaseReference databaseReference;
        ProgressDialog progressDialog;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ask_aquestion);



            databaseReference = FirebaseDatabase.getInstance().getReference().child("question posts");

            spinner = findViewById(R.id.spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Topics, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            questionEditText = findViewById(R.id.question_text);

            questionImageView = findViewById(R.id.question_Image);
            questionImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
                imageUri = data.getData();
                questionImageView.setImageURI(imageUri);
            }
        }

    public void Post(View view) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String askedBy = snapshot.child("name").getValue(String.class);
                String date = DateFormat.getDateInstance().format(new Date());
                String postid = databaseReference.push().getKey();
                String publisher = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String question = questionEditText.getText().toString().trim();
                String topic = spinner.getSelectedItem().toString();

                if (TextUtils.isEmpty(question)) {
                    Toast.makeText(AskAQuestionActivity.this, "Please ask a query", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (imageUri == null) {
                    // If no image is selected, upload the data without an image
                    progressDialog = new ProgressDialog(AskAQuestionActivity.this);
                    progressDialog.setMessage("Posting...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    Post post = new Post(askedBy, date, postid, publisher, question, null, topic);
                    databaseReference.child(postid).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            finish();
                        }
                    });
                } else {
                    // If an image is selected, upload the image to Firebase Storage first
                    progressDialog = new ProgressDialog(AskAQuestionActivity.this);
                    progressDialog.setMessage("Posting...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("question_images").child(postid);
                    storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Once the image is uploaded, save the image URL to the Realtime Database
                                    Post post = new Post(askedBy, date, postid, publisher, question, uri.toString(), topic);
                                    databaseReference.child(postid).setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            finish();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AskAQuestionActivity.this, "Operation canceled: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void Cancel(View view) {
        Intent intent =new Intent(AskAQuestionActivity.this,MainActivity.class);
        startActivity(intent);
    }
}



