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
import com.example.bps.myStoreItem;

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
        holder.itemName.setText(storeItemDetailList.get(position).getItemName());
        holder.itemImage.setImageResource(storeItemDetailList.get(position).getImageUrl());
        if (storeItemDetailList.size()-1 == position) {
            holder.itemPrice.setText("");
            holder.textView1.setText("");
            holder.textView2.setText("");
        }else{
            holder.itemPrice.setText(String.valueOf(storeItemDetailList.get(position).getItemPrice()));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, myStoreItem.class);
                intent.putExtra("ItemName",storeItemDetailList.get(position).getItemName());
                intent.putExtra("ItemPrice",storeItemDetailList.get(position).getItemPrice());
                intent.putExtra("ItemImageUrl",storeItemDetailList.get(position).getImageUrl());
                intent.putExtra("LastItem",storeItemDetailList.size() - position - 1);
                if (storeItemDetailList.size() - 1 != position) {
                    intent.putExtra("ItemId", storeItemDetailList.get(position).getItemId());
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

        TextView itemName, itemPrice, textView1, textView2;
        ImageView itemImage;
        CardView cardView;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.storeItem_itemName);
            itemPrice = itemView.findViewById(R.id.storeItem_itemPrice);
            itemImage = itemView.findViewById(R.id.storeItem_itemImage);
            textView1 = itemView.findViewById(R.id.storeItem_textView1);
            textView2 = itemView.findViewById(R.id.storeItem_textView2);
            cardView = (CardView) itemView.findViewById(R.id.storeItem_cardView);
        }
    }
}
