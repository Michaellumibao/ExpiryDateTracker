package com.lumibao.expirydatetracker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by micha on 2018-04-23.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Item> itemList;

    public RecyclerViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.itemList = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.list_item, parent, false);
        AdapterItem item = new AdapterItem(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        ((AdapterItem) holder).item_img.setImageResource(item.getImageID());
        ((AdapterItem) holder).item_title_txt.setText(item.getTitle());

        // Update expiry title
        int daysUntilExpired = item.getDaysUntilExpired();
        ((AdapterItem) holder).expiry_title_txt.setText("Days until expired: " + Integer.toString(daysUntilExpired));
        // Update status
        if (daysUntilExpired < 0) {
            ((AdapterItem) holder).status_txt.setText("EXPIRED");
            ((AdapterItem) holder).status_txt.setTextColor(Color.RED);
        } else if (daysUntilExpired >= 0 && daysUntilExpired <= 3) {
            ((AdapterItem) holder).status_txt.setText("OKAY");
            ((AdapterItem) holder).status_txt.setTextColor(0xFFDF541E);
        } else {
            ((AdapterItem) holder).status_txt.setText("GOOD");
            ((AdapterItem) holder).status_txt.setTextColor(Color.GREEN);
        }

        ((AdapterItem) holder).item_parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class AdapterItem extends RecyclerView.ViewHolder {
        ImageView item_img;
        TextView item_title_txt;
        TextView status_txt;
        TextView expiry_title_txt;
        RelativeLayout item_parent_layout;

        public AdapterItem(View itemView) {
            super(itemView);
            item_img = itemView.findViewById(R.id.item_img);
            item_title_txt=itemView.findViewById(R.id.item_title_txt);
            status_txt=itemView.findViewById(R.id.status_txt);
            expiry_title_txt=itemView.findViewById(R.id.expiry_title_txt);
            item_parent_layout = itemView.findViewById(R.id.item_parent_layout);
        }
    }
}
