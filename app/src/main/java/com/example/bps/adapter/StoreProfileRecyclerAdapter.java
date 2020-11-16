package com.example.bps.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bps.R;
import com.example.bps.model.BeaconInfo;

import java.util.ArrayList;
import java.util.List;

public class StoreProfileRecyclerAdapter extends RecyclerView.Adapter<StoreProfileRecyclerAdapter.MainViewHolder> {

    private Context context;
    private List<String> detail;
    private List<BeaconInfo> beaconList;

    public StoreProfileRecyclerAdapter(Context context, List<String> detail, List<BeaconInfo> beaconList) {
        this.context = context;
        this.detail = detail;
        this.beaconList = beaconList;
    }

    @Override
    public int getItemViewType(int position) {
        if(position < detail.size()){
            return 0;
        }else{
            return 1;
        }
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            return new StoreProfileRecyclerAdapter.MainViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.my_store_profile_card, parent, false));
        }else{
            return new StoreProfileRecyclerAdapter.MainViewHolder(LayoutInflater
                    .from(context).inflate(R.layout.beacon_card, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case 0:
                List<String> topics = new ArrayList<>();
                topics.add("Email");
                topics.add("เกี่ยวกับร้านค้า");
                topics.add("เบอร์โทรศัพท์");
                topics.add("ที่ตั้งร้าน");
                holder.topic.setText(topics.get(position));
                holder.detail.setText(detail.get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        TextView detail;
        CardView cardView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            topic = itemView.findViewById(R.id.myStoreProfile_card_topic);
            detail = itemView.findViewById(R.id.myStoreProfile_card_detail);
            cardView = (CardView) itemView.findViewById(R.id.myStoreProfile_cardView);
        }
    }
}
