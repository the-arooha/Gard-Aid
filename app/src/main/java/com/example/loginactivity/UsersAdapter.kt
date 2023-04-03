package com.example.loginactivity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsersAdapter(clickListener: ClickListener):RecyclerView.Adapter<UsersAdapter.ViewHolder>(),Filterable {

    private  var userModelList: List<UserModel> = arrayListOf();

    //search
    private  var userModelListFiltered: List<UserModel> = arrayListOf();
    //not search
    private  lateinit var context:Context;
    private var clickListener: ClickListener =clickListener;

    public  fun setData(userModel: List<UserModel>){
        this.userModelList=userModel
        //search
        this.userModelListFiltered=userModel
        //not search
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context=parent.context;
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_users,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var userModel = userModelList.get(position);
        var username = userModel.username;
        var prefix = username.substring(0,1)

        holder.tvUsername.text = username;
        holder.tvPrefix.text = prefix;

        holder.itemView.setOnClickListener{
            clickListener.clickedItem(userModel
            );
        }
    }
    override fun getItemCount(): Int {
         return userModelList.size
    }

    interface  ClickListener{
        fun clickedItem(userModel: UserModel);
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var tvUsername = itemView.findViewById<TextView>(R.id.tvUsername)
        var tvPrefix =itemView.findViewById<TextView>(R.id.tvPrefix)
    }

    override fun getFilter(): Filter {
        var filter = object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                var filterResults = FilterResults();
                if(p0==null || p0.isEmpty()){
                    filterResults.values = userModelListFiltered
                    filterResults.count = userModelListFiltered.size
                }else{
                    var searchChar = p0.toString().toLowerCase();

                    var filteredResults = ArrayList<UserModel>()

                    for(userModel in userModelListFiltered){
                        if(userModel.username.toLowerCase().contains(searchChar)){
                            filteredResults.add(userModel)
                        }
                    }

                    filterResults.values = filteredResults
                    filterResults.count = filteredResults.size
                }

                return filterResults;
            }


            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                userModelList= p1!!.values as List<UserModel>
                notifyDataSetChanged()
            }

        }

        return filter;
    }


}