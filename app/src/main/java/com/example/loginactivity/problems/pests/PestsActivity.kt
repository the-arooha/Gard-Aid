package com.example.loginactivity.problems.pests

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginactivity.R
import com.example.loginactivity.SelectedUserActivity
import com.example.loginactivity.UserModel
import com.example.loginactivity.UsersAdapter
import com.example.loginactivity.categories.vegetable.BroccoliActivity

class PestsActivity : AppCompatActivity(), UsersAdapter.ClickListener {
    private lateinit var rvUsers: RecyclerView;
    private  lateinit var usersAdapter: UsersAdapter;
    //search
    private lateinit var toolbar: Toolbar;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pests)
        initData();
    }

    private fun initData(){
        rvUsers = findViewById(R.id.rvUsers)
        //search
        toolbar = findViewById(R.id.tbToolbar)
        this.setSupportActionBar(toolbar)
        this.supportActionBar!!.title=""
        //not search
        initRecyclerView();
    }

    private fun initRecyclerView(){
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        usersAdapter = UsersAdapter(this);
        rvUsers.adapter = usersAdapter;
        showData();
    }

    private  fun populateUsers():List<UserModel>{
        var userList = ArrayList<UserModel>()
        userList.add(UserModel("Birds Eating Sunflower Leaves In The Garden"))
        userList.add(UserModel("Animals, Insects, And Bugs That Eat Plants At Night"))
        userList.add(UserModel("Crow Damage To Lawns – Why Are Crows Digging In Grass"))
        userList.add(UserModel("DIY Fungus Gnat Trap To Keep Pests Off Houseplants"))
        userList.add(UserModel("How To Get Rid Of Squash Bugs Naturally"))
        userList.add(UserModel("Common Garden Pests That Emerge After Rain"))
        userList.add(UserModel("Safe Plant Pesticides – Indoor Pesticides For Edible Plants"))
        userList.add(UserModel("Can Insecticides Hurt Plants – Learn About Insecticide Plant Injury"))
        userList.add(UserModel("Soap For Garden Use: Using Bar Soap In The Garden And Beyond"))
        userList.add(UserModel("DIY Fruit Tree Pepper Spray – How To Use Hot Peppers For Fruit Trees"))
        return userList;
    }
    private fun showData(){
        usersAdapter.setData(populateUsers())
    }
    override fun clickedItem(userModel: UserModel) {
        when (userModel.username) {
            "Birds Eating Sunflower Leaves In The Garden" -> startActivity(Intent(this, BirdssunActivity::class.java))
             // add more cases for each item in the list
            else -> {
                // handle unknown item
                startActivity(Intent(this, SelectedUserActivity::class.java).putExtra("username",userModel.username))
            }
        }
    }
    //search
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        var menuItem = menu!!.findItem(R.id.searchView)
        var searchView: SearchView = menuItem.actionView as SearchView
        searchView.maxWidth=Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                usersAdapter.filter.filter(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return super.onOptionsItemSelected(item)
//    }


}