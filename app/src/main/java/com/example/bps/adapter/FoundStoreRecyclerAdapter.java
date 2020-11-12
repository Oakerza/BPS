package com.example.bps.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bps.R;
import com.example.bps.foundStore;
import com.example.bps.model.FoundStoreDetail;

import java.util.List;

public class FoundStoreRecyclerAdapter extends RecyclerView
        .Adapter<FoundStoreRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<FoundStoreDetail> foundStoreDetailList;

    public FoundStoreRecyclerAdapter(Context context,
                                     List<FoundStoreDetail>foundStoreDetailList){
        this.context = context;
        this.foundStoreDetailList = foundStoreDetailList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.found_store_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.storeName.setText(foundStoreDetailList.get(position).getStoreName());
        holder.storeIcon.setImageResource(foundStoreDetailList.get(position).getImageUrl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,foundStore.class);
                intent.putExtra("StoreName",foundStoreDetailList
                        .get(position).getStoreName());
                intent.putExtra("StoreIconUrl",foundStoreDetailList
                        .get(position).getImageUrl());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return foundStoreDetailList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder{

        TextView storeName;
        ImageView storeIcon;
        CardView cardView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            storeName = itemView.findViewById(R.id.foundStoreCard_name);
            storeIcon = itemView.findViewById(R.id.foundStoreCard_icon);
            cardView = (CardView) itemView.findViewById(R.id.foundStoreCard_cartView);

        }
    }
}
