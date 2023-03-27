package com.example.loginactivity;

import static com.example.loginactivity.R.id.drawer_layout;
import static com.example.loginactivity.R.id.navigation_view;
import static com.example.loginactivity.R.id.topAppbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginactivity.Adapters.PostAdapter;
import com.example.loginactivity.Model.Post;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategorySelectedActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    private  String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selected);


        toolbar=findViewById(R.id.topAppbar);
        setSupportActionBar(toolbar);




        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        postList=new ArrayList<>();
        postAdapter=new PostAdapter(CategorySelectedActivity.this,postList);
        recyclerView.setAdapter(postAdapter);

        if(getIntent().getExtras()!=null){
            title=getIntent().getStringExtra("title");
            getSupportActionBar().setTitle(title);

            readPosts();
        }

    }

    private  void readPosts(){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("question posts");
        Query query=reference.orderByChild("topic").equalTo(title);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Post post=dataSnapshot.getValue(Post.class);
                    postList.add(post);
                }

                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(CategorySelectedActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}