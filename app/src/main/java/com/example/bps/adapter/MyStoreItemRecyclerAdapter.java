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
import com.example.bps.model.StoreItemDetail;
import com.example.bps.MyStoreItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyStoreItemRecyclerAdapter extends RecyclerView.Adapter<MyStoreItemRecyclerAdapter.MainViewHolder> {
    private Context context;
    private List<StoreItemDetail> storeItemDetailList;

    public MyStoreItemRecyclerAdapter(Context context, List<StoreItemDetail> storeItemDetailList) {
        this.context = context;
        this.storeItemDetailList = storeItemDetailList;
    }

    @NonNull
    @Override
    public MyStoreItemRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyStoreItemRecyclerAdapter.MainViewHolder(LayoutInflater.from(context).inflate(R.layout.store_item_card, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyStoreItemRecyclerAdapter.MainViewHolder holder, int position) {
        StoreItemDetail storeItemDetail = storeItemDetailList.get(position);
        holder.itemName.setText(storeItemDetail.getItemName());
        if (storeItemDetailList.size()-1 == position) {
            holder.itemPrice.setText("");
            holder.textView1.setText("");
            holder.itemImage.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
        }else{
            holder.itemPrice.setText(String.valueOf(storeItemDetail.getItemPrice()));
            Picasso.with(context)
                    .load(storeItemDetail.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .fit()
                    .centerCrop()
                    .into(holder.itemImage);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyStoreItem.class);
                intent.putExtra("ItemName",storeItemDetail.getItemName());
                intent.putExtra("ItemPrice",storeItemDetail.getItemPrice());
                intent.putExtra("LastItem",storeItemDetailList.size() - position - 1);
                if (storeItemDetailList.size() - 1 != position) {
                    intent.putExtra("ItemId", storeItemDetail.getItemId());
                    intent.putExtra("ItemImageUrl",storeItemDetail.getImageUrl());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeItemDetailList.size();
    }

    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        TextView itemName, itemPrice, textView1;
        ImageView itemImage;
        CardView cardView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.storeItem_itemName);
            itemPrice = itemView.findViewById(R.id.storeItem_itemPrice);
            itemImage = itemView.findViewById(R.id.storeItem_itemImage);
            textView1 = itemView.findViewById(R.id.storeItem_textView1);
            cardView = (CardView) itemView.findViewById(R.id.storeItem_cardView);
        }
    }
}
