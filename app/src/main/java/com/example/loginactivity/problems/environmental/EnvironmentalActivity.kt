package com.example.loginactivity.problems.environmental

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

class EnvironmentalActivity : AppCompatActivity(), UsersAdapter.ClickListener {
    private lateinit var rvUsers: RecyclerView;
    private  lateinit var usersAdapter: UsersAdapter;
    //search
    private lateinit var toolbar: Toolbar;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_environmental)
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
        userList.add(UserModel("How To Prevent Trees Exploding From Cold This Winter"))
        userList.add(UserModel("How To Predict Frost And Protect Your Garden"))
        userList.add(UserModel("What Is Lake Effect Snow And How Does It Impact Gardens?"))
        userList.add(UserModel("Warning Signs Plants Are Too Cold… And When It’s Too Late"))
        userList.add(UserModel("How To Diagnose And Prevent Splitting Fruit"))
        userList.add(UserModel("Cool Weather Crops: How Freezing Temperatures Affect Cold Hardy Plants"))
        userList.add(UserModel("Frost Damage On Plants: Early Blooming Flowers And Freezing Temperatures"))
        userList.add(UserModel("Patches Of Brown Leaves On Trees – Fall Color, Cicada Damage Or Something Else?"))
        userList.add(UserModel("How Does Smoke Affect Gardens – Caring For Smoke Damaged Plants"))
        userList.add(UserModel("Dark Side Of Nature – Sinister Plants To Avoid In The Garden"))
        userList.add(UserModel("Flowers Toxic To Bees: What Plants Are Poisonous To Bees"))
        userList.add(UserModel("Saving Dried Plants: Information On Reviving Drought Stressed Plants"))
        return userList;
    }
    private fun showData(){
        usersAdapter.setData(populateUsers())
    }
    override fun clickedItem(userModel: UserModel) {
        when (userModel.username) {
            "How To Prevent Trees Exploding From Cold This Winter" -> startActivity(Intent(this, ExfcoldActivity::class.java))
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