package com.lumibao.expirydatetracker;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by micha on 2018-04-19.
 */

public class Item implements Serializable{
    private int imageID;
    private String title;
    private String status;
    private String expiryText;
    private int daysUntilExpired;
    private Calendar expiryDay;

    public Item(int imageID, String title, Calendar expiryDay) {
        this.imageID = imageID;
        this.title = title;
        this.expiryDay = expiryDay;
        this.daysUntilExpired = calculateDaysUntilExpired();

        long timeBetweenDates = expiryDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        Log.i("~~~Time", Double.toString(timeBetweenDates / 1000 / 60 / 60 / 24));
    }

    public int calculateDaysUntilExpired() {
        long timeBetweenDates = expiryDay.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
        return (int)timeBetweenDates / 1000 / 60 / 60 / 24;
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
