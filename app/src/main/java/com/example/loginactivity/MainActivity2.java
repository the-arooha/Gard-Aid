package com.example.loginactivity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.loginactivity.Adapters.StoreListAdapter;
import com.example.loginactivity.Model.StoreModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements StoreListAdapter.StoreListClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //ActionBar actionBar=getSupportActionBar();
        //actionBar.setTitle("Store List");

        List<StoreModel> storeModelList= getStoreData();
        initRecyclerView(storeModelList);
    }

    private void initRecyclerView(List<StoreModel> storeModelList){
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StoreListAdapter adapter=new StoreListAdapter(storeModelList,this);
        recyclerView.setAdapter(adapter);
    }
    private List<StoreModel> getStoreData(){
        InputStream is=getResources().openRawResource(R.raw.store);
        Writer writer=new StringWriter();
        char[] buffer=new char[1024];
        try{
            Reader reader= new BufferedReader(new InputStreamReader(is,"UTF-8"));
            int n;
            while((n=reader.read(buffer))!=-1){
                writer.write(buffer,0,n);
            }
        }catch (Exception e){

        }

        String jsonStr=writer.toString();
        Gson gson=new Gson();
        StoreModel[] storeModels=gson.fromJson(jsonStr,StoreModel[].class);
        List<StoreModel> restList= Arrays.asList(storeModels);

        return  restList;
    }

    @Override
    public void onItemClick(StoreModel storeModel) {
        Intent intent=new Intent(MainActivity2.this,StoreMenuActivity.class);
        intent.putExtra("StoreModel",storeModel);
        startActivity(intent);
    }
}