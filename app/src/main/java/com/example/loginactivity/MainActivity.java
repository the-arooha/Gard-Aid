package com.example.loginactivity;

import static com.example.loginactivity.R.id.drawer_layout;
import static com.example.loginactivity.R.id.nav_basics;
import static com.example.loginactivity.R.id.nav_cart;
import static com.example.loginactivity.R.id.nav_environmental;
import static com.example.loginactivity.R.id.nav_feed;
import static com.example.loginactivity.R.id.nav_foliage;
import static com.example.loginactivity.R.id.nav_herb;
import static com.example.loginactivity.R.id.nav_ingredients;
import static com.example.loginactivity.R.id.nav_manures;
import static com.example.loginactivity.R.id.nav_meditation;
import static com.example.loginactivity.R.id.nav_morecat;
import static com.example.loginactivity.R.id.nav_pests;
import static com.example.loginactivity.R.id.nav_plant;
import static com.example.loginactivity.R.id.nav_profile;
import static com.example.loginactivity.R.id.nav_share;
import static com.example.loginactivity.R.id.nav_tea;
import static com.example.loginactivity.R.id.nav_vegetable;
import static com.example.loginactivity.R.id.nav_vermi;
import static com.example.loginactivity.R.id.navigation_view;
import static com.example.loginactivity.R.id.question_text;
import static com.example.loginactivity.R.id.topAppbar;
import static com.example.loginactivity.R.layout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginactivity.Adapters.PostAdapter;
import com.example.loginactivity.Model.Post;
import com.example.loginactivity.categories.MainActivity4;
import com.example.loginactivity.categories.foliage.FoliageActivity;
import com.example.loginactivity.categories.herb.HerbActivity;
import com.example.loginactivity.categories.herbaltea.HerbalteaActivity;
import com.example.loginactivity.categories.meditation.MeditationActivity;
import com.example.loginactivity.categories.vegetable.VegetableActivity;
import com.example.loginactivity.compost.basics.BasicsActivity;
import com.example.loginactivity.compost.ingredients.IngredientsActivity;
import com.example.loginactivity.compost.manures.ManuresActivity;
import com.example.loginactivity.compost.verm.VermActivity;
import com.example.loginactivity.problems.environmental.EnvironmentalActivity;
import com.example.loginactivity.problems.pests.PestsActivity;
import com.example.loginactivity.problems.plantdisease.PlantdiseasesActivity;
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
                case nav_feed:
                    Intent intent1=new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent1);
                    break;
            //categories
                case nav_herb:
                    Intent intent2=new Intent(MainActivity.this, HerbActivity.class);
                    startActivity(intent2);
                    break;
                case nav_vegetable:
                    Intent intent3=new Intent(MainActivity.this, VegetableActivity.class);
                    startActivity(intent3);
                    break;
                case nav_meditation:
                    Intent intent4=new Intent(MainActivity.this, MeditationActivity.class);
                    startActivity(intent4);
                    break;
                case nav_tea:
                    Intent intent5=new Intent(MainActivity.this, HerbalteaActivity.class);
                    startActivity(intent5);
                    break;
                case nav_foliage:
                    Intent intent6=new Intent(MainActivity.this, FoliageActivity.class);
                    startActivity(intent6);
                    break;
                case nav_morecat:
                    Intent intent7=new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(intent7);
                    break;
                //problems
                case nav_plant:
                    Intent intent8=new Intent(MainActivity.this, PlantdiseasesActivity.class);
                    startActivity(intent8);
                    break;
                case nav_environmental:
                    Intent intent9=new Intent(MainActivity.this, EnvironmentalActivity.class);
                    startActivity(intent9);
                    break;
                case nav_pests:
                    Intent intent10=new Intent(MainActivity.this, PestsActivity.class);
                    startActivity(intent10);
                    break;
                //composting
                case nav_basics:
                    Intent intent11=new Intent(MainActivity.this, BasicsActivity.class);
                    startActivity(intent11);
                    break;
                case nav_ingredients:
                    Intent intent12=new Intent(MainActivity.this, IngredientsActivity.class);
                    startActivity(intent12);
                    break;
                case nav_manures:
                    Intent intent13=new Intent(MainActivity.this, ManuresActivity.class);
                    startActivity(intent13);
                    break;
                case nav_vermi:
                    Intent intent14=new Intent(MainActivity.this, VermActivity.class);
                    startActivity(intent14);
                    break;
                //others
                case nav_profile:
                    Intent intent15=new Intent(MainActivity.this,userprofile.class);
                    startActivity(intent15);
                    break;
                case nav_cart:
                    Intent intent16=new Intent(MainActivity.this,SplashActivity.class);
                    startActivity(intent16);
                    break;
                case nav_share:
                    Intent intent17=new Intent(Intent.ACTION_SEND);
                    intent17.setType("text/plain");
                    intent17.putExtra(Intent.EXTRA_SUBJECT,"Check out this community based educational query application");
                    intent17.putExtra(Intent.EXTRA_TEXT,"Your Application Link");
                    startActivity(Intent.createChooser(intent17,"Share via"));
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