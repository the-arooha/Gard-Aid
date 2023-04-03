package com.example.loginactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SelectedUserActivity : AppCompatActivity() {

    private  lateinit var tvSelectedUser: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_user)
        initData();
    }

    private fun initData(){
        tvSelectedUser=findViewById(R.id.tvSelectedUser)
        getData();
    }

    private fun getData(){
        var intent =intent.extras

        var username = intent!!.getString("username")

        tvSelectedUser.text = username;
    }

}