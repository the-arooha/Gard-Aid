package com.example.loginactivity.compost.basics

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

class BasicsActivity : AppCompatActivity(), UsersAdapter.ClickListener {
    private lateinit var rvUsers: RecyclerView;
    private  lateinit var usersAdapter: UsersAdapter;
    //search
    private lateinit var toolbar: Toolbar;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basics)
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
        userList.add(UserModel("How To Reduce Food Waste In Landfills By Composting"))
        userList.add(UserModel("In-Garden Composting Techniques And Tips"))
        userList.add(UserModel("Compost Spreading Tools For Farmers And Gardeners"))
        userList.add(UserModel("When And How To Harvest Compost"))
        userList.add(UserModel("How To Add Compost To Established Garden Beds"))
        userList.add(UserModel("How To Compost In A 5 Gallon Bucket"))
        userList.add(UserModel("How To Make Compost In-Ground"))
        userList.add(UserModel("Compost Bag DIY – Can You Make Compost In A Bag"))
        userList.add(UserModel("Add As You Go Composting – What Is Passive Composting"))
        userList.add(UserModel("Balcony Composting Info – Can You Compost On A Balcony"))
        userList.add(UserModel("What Is Drunken Composting – How To Make Drunken Compost"))
        userList.add(UserModel("Is My Compost Dead: Tips For Reviving Old Compost"))
        return userList;
    }
    private fun showData(){
        usersAdapter.setData(populateUsers())
    }
    override fun clickedItem(userModel: UserModel) {
        when (userModel.username) {
            "How To Reduce Food Waste In Landfills By Composting" -> startActivity(Intent(this, LandfillsActivity::class.java).putExtra("username", userModel.username))
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