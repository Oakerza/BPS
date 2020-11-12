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
import com.example.bps.foundStoreItem;
import com.example.bps.model.StoreItemDetail;

import java.util.List;

public class StoreItemRecyclerAdapter extends RecyclerView.Adapter<StoreItemRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<StoreItemDetail> storeItemDetailList;

    public StoreItemRecyclerAdapter(Context context, List<StoreItemDetail> storeItemDetailList) {
        this.context = context;
        this.storeItemDetailList = storeItemDetailList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.store_item_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.itemName.setText(storeItemDetailList.get(position).getItemName());
        holder.itemPrice.setText(storeItemDetailList.get(position).getItemPrice().toString());
        holder.itemImage.setImageResource(storeItemDetailList.get(position).getImageUrl());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, foundStoreItem.class);
                intent.putExtra("ItemName",storeItemDetailList.get(position).getItemName());
                intent.putExtra("ItemPrice",storeItemDetailList.get(position).getItemPrice());
                intent.putExtra("ItemImageUrl",storeItemDetailList.get(position).getImageUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItemDetailList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        TextView itemName;
        TextView itemPrice;
        ImageView itemImage;
        CardView cardView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.storeItem_itemName);
            itemPrice = itemView.findViewById(R.id.storeItem_itemPrice);
            itemImage = itemView.findViewById(R.id.storeItem_itemImage);
            cardView = (CardView) itemView.findViewById(R.id.storeItem_cardView);
        }
    }
}
