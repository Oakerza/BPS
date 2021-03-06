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
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoundStoreRecyclerAdapter extends RecyclerView
        .Adapter<FoundStoreRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<FoundStoreDetail> foundStoreDetailList;

    public FoundStoreRecyclerAdapter(Context context,
                                     List<FoundStoreDetail> foundStoreDetailList) {
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
        FoundStoreDetail foundStoreDetail = foundStoreDetailList.get(position);
        holder.storeName.setText(foundStoreDetail.getStoreName());
        if (foundStoreDetail.getImageUrl() == null) {
            holder.storeIcon.setImageResource(R.drawable.user_image);
        } else {
            Picasso.with(context)
                    .load(foundStoreDetail.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(holder.storeIcon);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, foundStore.class);
                intent.putExtra("StoreName", foundStoreDetail.getStoreName());
                intent.putExtra("StoreIconUrl", foundStoreDetail.getImageUrl());
                intent.putExtra("storeID", foundStoreDetail.getStoreId());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return foundStoreDetailList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

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
