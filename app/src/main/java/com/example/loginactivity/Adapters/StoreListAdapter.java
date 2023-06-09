package com.example.loginactivity.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.loginactivity.Model.StoreModel;
import com.example.loginactivity.R;

import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.MyViewHolder> {

    private List<StoreModel> storeModelList;
    private StoreListClickListener clickListener;

    public StoreListAdapter(List<StoreModel> storeModelList, StoreListClickListener clickListener) {
        this.storeModelList = storeModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<StoreModel> storeModelList) {
        this.storeModelList = storeModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.storeName.setText(storeModelList.get(holder.getAdapterPosition()).getName());
        holder.storeAddress.setText(storeModelList.get(holder.getAdapterPosition()).getAddress());
        holder.storeHours.setText("Time:" + storeModelList.get(holder.getAdapterPosition()).getHours().getTodayHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(storeModelList.get(holder.getAdapterPosition()));
            }
        });

        Glide.with(holder.thumbImage)
                .load(storeModelList.get(holder.getAdapterPosition()).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return storeModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView storeAddress;
        TextView storeHours;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            storeName = view.findViewById(R.id.storeName);
            storeAddress = view.findViewById(R.id.storeAddress);
            storeHours = view.findViewById(R.id.storeHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface StoreListClickListener {
        void onItemClick(StoreModel storeModel);
    }
}
