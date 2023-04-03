package com.example.loginactivity.compost.ingredients

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
import com.example.loginactivity.problems.environmental.ExfcoldActivity

class IngredientsActivity : AppCompatActivity(), UsersAdapter.ClickListener {
    private lateinit var rvUsers: RecyclerView;
    private  lateinit var usersAdapter: UsersAdapter;
    //search
    private lateinit var toolbar: Toolbar;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredients)
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
        userList.add(UserModel("Can You Compost Yard Waste – Ways To Compost Garden Waste"))
        userList.add(UserModel("What To Do With Compostable Containers"))
        userList.add(UserModel("Can You Compost Wood Chips – Tips For Composting Wood Chips"))
        userList.add(UserModel("Can You Compost Straw – Is It Safe To Put Straw In Compost"))
        userList.add(UserModel("Can You Compost Wine: Learn About Wine’s Effect On Compost"))
        userList.add(UserModel("Home Brew Composting Info – Can You Compost Spent Grains"))
        userList.add(UserModel("Liquid Composting Tips: Can You Compost Liquids"))
        userList.add(UserModel("Composting Potato Peelings: How Do You Compost Potato Skins"))
        userList.add(UserModel("Can Bread Be Composted: Tips For Composting Bread"))
        return userList;
    }
    private fun showData(){
        usersAdapter.setData(populateUsers())
    }
    override fun clickedItem(userModel: UserModel) {
        when (userModel.username) {
            "Can You Compost Yard Waste – Ways To Compost Garden Waste" -> startActivity(Intent(this, YardwasteActivity::class.java))
            //"What To Do With Compostable Containers" -> startActivity(Intent(this, BroccoliActivity::class.java).putExtra("username", userModel.username))
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