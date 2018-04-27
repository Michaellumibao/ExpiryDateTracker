package com.lumibao.expirydatetracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

/**
 * Created by micha on 2018-04-19.
 */

public class ItemAdapter extends BaseAdapter {

    private Context mContext;
    List<Item> itemList;

    ItemAdapter(Context context, List<Item> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        // Prepare list_item.xml
        View v = View.inflate(mContext, R.layout.list_item, null);

        // Reference all the elements in list_item.xml
        ImageView item_img = (ImageView) v.findViewById(R.id.item_img);
        TextView item_title_txt = (TextView) v.findViewById(R.id.item_title_txt);
        TextView status_txt = (TextView) v.findViewById(R.id.status_txt);
        TextView expiry_title_txt = (TextView) v.findViewById(R.id.expiry_title_txt);

        // Update image
        Resources resources = mContext.getResources();
        int imageID = resources.getIdentifier(itemList.get(i).getImageName(), "drawable", "com.lumibao.expirydatetracker");
        item_img.setImageResource(imageID);
        // Update title
        item_title_txt.setText(itemList.get(i).getTitle());
        // Save days until expired
        int daysUntilExpired = itemList.get(i).getDaysUntilExpired();
        // Update expiry title
        expiry_title_txt.setText("Days until expired: " + Integer.toString(daysUntilExpired));
        // Update status
        if (daysUntilExpired < 0) {
            status_txt.setText("EXPIRED");
            status_txt.setTextColor(Color.RED);
        }else if (daysUntilExpired >= 0 && daysUntilExpired <=3 ){
            status_txt.setText("OKAY");
            status_txt.setTextColor(0xFFDF541E);
        } else {
            status_txt.setText("GOOD");
            status_txt.setTextColor(Color.GREEN);
        }

        return v;
    }

}
