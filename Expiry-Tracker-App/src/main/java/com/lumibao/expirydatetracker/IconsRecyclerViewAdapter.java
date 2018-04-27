package com.lumibao.expirydatetracker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    List<String[]> imageNameList;

    public IconsRecyclerViewAdapter(Context context, List<String[]> imageNameList){
        this.context = context;
        this.imageNameList = imageNameList;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final int index = position;


        ((IconAdapterItem)holder).icon_1.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), context.getResources()
                .getIdentifier(imageNameList.get(position)[0], "drawable",
                        "com.lumibao.expirydatetracker"), 100,100));
        ((IconAdapterItem)holder).icon_2.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), context.getResources()
                .getIdentifier(imageNameList.get(position)[1], "drawable",
                        "com.lumibao.expirydatetracker"), 100,100));
        ((IconAdapterItem)holder).icon_3.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), context.getResources()
                .getIdentifier(imageNameList.get(position)[2], "drawable",
                        "com.lumibao.expirydatetracker"), 100,100));

        ((IconAdapterItem)holder).icon_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof AddOrEditActivity) {
                    ((AddOrEditActivity)context).updateImage(imageNameList.get(index)[0]);
                }
            }
        });
        ((IconAdapterItem)holder).icon_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof AddOrEditActivity) {
                    ((AddOrEditActivity)context).updateImage(imageNameList.get(index)[1]);
                }
            }
        });
        ((IconAdapterItem)holder).icon_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof AddOrEditActivity) {
                    ((AddOrEditActivity)context).updateImage(imageNameList.get(index)[2]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageNameList.size();
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

    // Reduce image sizes to be loaded in memory.
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
