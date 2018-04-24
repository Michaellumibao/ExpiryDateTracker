package com.lumibao.expirydatetracker;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by micha on 2018-04-24.
 */

public class IconsRecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<int[]> imageIdList;

    public IconsRecyclerViewAdapter(Context context, List<int[]> imageIdList){
        this.context = context;
        this.imageIdList = imageIdList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View column = inflater.inflate(R.layout.icons_view, parent, false);
        IconAdapterItem item = new IconAdapterItem(column);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((IconAdapterItem)holder).icon_1.setImageResource(imageIdList.get(position)[0]);
        ((IconAdapterItem)holder).icon_2.setImageResource(imageIdList.get(position)[1]);
        ((IconAdapterItem)holder).icon_3.setImageResource(imageIdList.get(position)[2]);
    }

    @Override
    public int getItemCount() {
        return imageIdList.size();
    }

    public class IconAdapterItem extends RecyclerView.ViewHolder {
        ImageView icon_1;
        ImageView icon_2;
        ImageView icon_3;
        public IconAdapterItem(View itemView) {
            super(itemView);
            icon_1 = itemView.findViewById(R.id.icon_1);
            icon_2 = itemView.findViewById(R.id.icon_2);
            icon_3 = itemView.findViewById(R.id.icon_3);
        }
    }
}
