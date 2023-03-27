package com.example.loginactivity;

import static com.example.loginactivity.R.id.drawer_layout;
import static com.example.loginactivity.R.id.nav_arts;
import static com.example.loginactivity.R.id.nav_commerce;
import static com.example.loginactivity.R.id.nav_currentaffairs;
import static com.example.loginactivity.R.id.nav_physicaleducation;
import static com.example.loginactivity.R.id.nav_profile;
import static com.example.loginactivity.R.id.nav_science;
import static com.example.loginactivity.R.id.nav_share;
import static com.example.loginactivity.R.id.nav_technology;
import static com.example.loginactivity.R.id.nav_tests;
import static com.example.loginactivity.R.id.navigation_view;
import static com.example.loginactivity.R.id.question_text;
import static com.example.loginactivity.R.id.topAppbar;
import static com.example.loginactivity.R.layout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.Adapters.PostAdapter;
import com.example.loginactivity.Model.Post;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private ProgressBar progress_circular;

    ArrayAdapter<Post> arrayAdapter;
    ListView listView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        listView=findViewById(R.id.listview);
        arrayAdapter=new ArrayAdapter<Post>(this, android.R.layout.simple_list_item_1,question_text);
        listView.setAdapter(arrayAdapter);

        fab=findViewById(R.id.fab);
        recyclerView=findViewById(R.id.recyclerView);
        progress_circular=findViewById(R.id.progress_circular);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        MaterialToolbar toolbar=findViewById(topAppbar);
        DrawerLayout drawerLayout=findViewById(drawer_layout);
        NavigationView navigationView=findViewById(navigation_view);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AskAQuestionActivity.class);
                startActivity(intent);

            }
        });

        postList=new ArrayList<>();
        postAdapter=new PostAdapter(MainActivity.this,postList);
        recyclerView.setAdapter(postAdapter);
        readQuestionsPosts();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id=item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (id)
            {
                case nav_profile:
                    Intent intent=new Intent(MainActivity.this,userprofile.class);
                    startActivity(intent);
                    break;
                case nav_science:
                    Intent intent1=new Intent(MainActivity.this,CategorySelectedActivity.class);
                    intent1.putExtra("title","Science");
                    startActivity(intent1);
                    break;
                case nav_arts:
                    Intent intent2=new Intent(MainActivity.this,CategorySelectedActivity.class);
                    intent2.putExtra("title","Arts");
                    startActivity(intent2);
                    break;
                case nav_commerce:
                    Intent intent3=new Intent(MainActivity.this,CategorySelectedActivity.class);
                    intent3.putExtra("title","Commerce");
                    startActivity(intent3);
                    break;
                case nav_technology:
                    Intent intent4=new Intent(MainActivity.this,CategorySelectedActivity.class);
                    intent4.putExtra("title","Technology");
                    startActivity(intent4);
                    break;
                case nav_physicaleducation:
                    Intent intent5=new Intent(MainActivity.this,CategorySelectedActivity.class);
                    intent5.putExtra("title","Physical Education");
                    startActivity(intent5);
                    break;
                case nav_currentaffairs:
                    Toast.makeText(MainActivity.this, "Current Affairs", Toast.LENGTH_SHORT).show();break;
                case nav_tests:
                    Toast.makeText(MainActivity.this, "Tests", Toast.LENGTH_SHORT).show();break;
                case nav_share:
                    Intent intent6=new Intent(Intent.ACTION_SEND);
                    intent6.setType("text/plain");
                    intent6.putExtra(Intent.EXTRA_SUBJECT,"Check out this community based educational query application");
                    intent6.putExtra(Intent.EXTRA_TEXT,"Your Application Link");
                    startActivity(Intent.createChooser(intent6,"Share via"));
                    return super.onOptionsItemSelected(item);

                default:
                    return true;

            }

            return true;
        });
    }

    private void readQuestionsPosts() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("question posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Post post=dataSnapshot.getValue(Post.class);
                    postList.add(post);


                }
                postAdapter.notifyDataSetChanged();
                progress_circular.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search,menu);
//        MenuItem menuItem=menu.findItem(R.id.search);
//        SearchView searchView=(SearchView) menuItem.getActionView();
//
//        searchView.setQueryHint("Type here to search");
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                mySearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                mySearch(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

//    private  void mySearch(String newText){
//        FirebaseRecyclerOptions<Post> options=
//                new FirebaseRecyclerOptions.Builder<Post>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("question posts").orderByChild("question").startAt(newText).endAt(newText+"~"),Post.class)
//                        .build();
//
//
//       postAdapter= new Post(options);
//       postAdapter.startListening();
//      recyclerView.setAdapter(postAdapter);

//
//    }

}