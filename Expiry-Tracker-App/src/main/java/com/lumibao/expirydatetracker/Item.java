package com.lumibao.expirydatetracker;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by micha on 2018-04-19.
 */

public class Item implements Serializable{
    private int imageID;
    private String title;
    private String status;
    private String expiryText;
    private int daysUntilExpired;

    public Item(int imageID, String title, int daysUntilExpired) {
        this.imageID = imageID;
        this.title = title;
        this.daysUntilExpired = daysUntilExpired;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDaysUntilExpired() {
        return daysUntilExpired;
    }

    public void setDaysUntilExpired(int daysUntilExpired) {
        this.daysUntilExpired = daysUntilExpired;
    }
}
