package com.example.loginactivity.categories.meditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loginactivity.R
import com.example.loginactivity.SelectedUserActivity
import com.example.loginactivity.UserModel
import com.example.loginactivity.UsersAdapter
import com.example.loginactivity.categories.herb.ParsleyActivity
import com.example.loginactivity.categories.vegetable.BroccoliActivity

class MeditationActivity : AppCompatActivity(), UsersAdapter.ClickListener {
    private lateinit var rvUsers: RecyclerView
    private  lateinit var usersAdapter: UsersAdapter
    //search
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meditation)
        initData()
    }

    private fun initData(){
        rvUsers = findViewById(R.id.rvUsers)
        //search
        toolbar = findViewById(R.id.tbToolbar)
        this.setSupportActionBar(toolbar)
        this.supportActionBar!!.title=""
        //not search
        initRecyclerView()
    }

    private fun initRecyclerView(){
        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        usersAdapter = UsersAdapter(this)
        rvUsers.adapter = usersAdapter
        showData()
    }

    private  fun populateUsers():List<UserModel>{
        var userList = ArrayList<UserModel>()
        userList.add(UserModel("Milkweed"))
        userList.add(UserModel("Red Beebalm"))
        userList.add(UserModel("Liatris"))
        userList.add(UserModel("Japanese Silver Grass"))
        userList.add(UserModel("Sunflowers"))
        userList.add(UserModel("Blood Grass"))
        userList.add(UserModel("Karl Foerster Reed Gras"))
        userList.add(UserModel("Blazing Star"))
        userList.add(UserModel("Pale Purple Coneflower"))
        userList.add(UserModel("Swamp Milkweed"))
        userList.add(UserModel("Garden Phiox"))
        userList.add(UserModel("Ohio Spiderwort"))
        userList.add(UserModel("Sedum"))
        userList.add(UserModel("Sea Holly"))
        userList.add(UserModel("Butterfly Weed"))
        userList.add(UserModel("Little Bluestem"))
        userList.add(UserModel("Spike Speedwell"))
        userList.add(UserModel("Labyrinth Mindfullness Garden"))

        return userList
    }
    private fun showData(){

        usersAdapter.setData(populateUsers())
    }

    override fun clickedItem(userModel: UserModel) {
        when (userModel.username) {
            "Milkweed" -> startActivity(Intent(this, MilkweedActivity::class.java))
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