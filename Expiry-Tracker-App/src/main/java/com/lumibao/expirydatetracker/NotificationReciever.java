package com.lumibao.expirydatetracker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by micha on 2018-04-25.
 */

public class NotificationReciever extends BroadcastReceiver{

    List<Item> itemList;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, MainActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeatingIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Load data
        Gson gson = new Gson();
        String json = intent.getStringExtra("itemList");
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        itemList = gson.fromJson(json, type);

        for (Item item: itemList) {
            item.recalculateDaysUntilExpired();
        }


        // Expired notification
        NotificationItem expiredNotification = getExpired();
        if (expiredNotification != null) {
            Notification notificationBuilder = new Notification.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.app_icon_round)
                    .setContentTitle(expiredNotification.title)
                    .setContentText(expiredNotification.message)
                    .setNumber(expiredNotification.number)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources() , expiredNotification.notificationImage))
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(100, notificationBuilder);
        }
        // Expires tomorrow notification
        NotificationItem expiresTomorrowNotification = getExpiresTomorrow();
        if (expiresTomorrowNotification != null) {
            Notification notificationBuilder = new Notification.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.app_icon_round)
                    .setContentTitle(expiresTomorrowNotification.title)
                    .setContentText(expiresTomorrowNotification.message)
                    .setNumber(expiresTomorrowNotification.number)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources() , expiresTomorrowNotification.notificationImage))
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(101, notificationBuilder);
        }
        // Expire notification
        NotificationItem expiresSoonNotification = getExpiresSoon();
        if (expiresSoonNotification != null) {
            Notification notificationBuilder = new Notification.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.app_icon_round)
                    .setContentTitle(expiresSoonNotification.title)
                    .setContentText(expiresSoonNotification.message)
                    .setNumber(expiresSoonNotification.number)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources() , expiresSoonNotification.notificationImage))
                    .setAutoCancel(true)
                    .build();
            notificationManager.notify(102, notificationBuilder);
        }
    }

    public class NotificationItem {
        public String title;
        public String message;
        public int number;
        public int notificationImage;

        public NotificationItem(String title, String message, int number, int notificationImage) {
            this.title = title;
            this.message = message;
            this.number = number;
            this.notificationImage = notificationImage;
        }
    }

    //~~~ EXPIRED ~~~
    public NotificationItem getExpired() {
        List<String> expiredItems = new ArrayList<>();
        String expiredTitle = "";
        String expiredMessage = "";
        int numberExpired = 0;
        int expiredImage = 0;

        if (itemList != null) {
            for (Item item : itemList) {
                if (item.getDaysUntilExpired() < 0) {
                    numberExpired++;
                    expiredItems.add(item.getTitle());
                    expiredImage = context.getResources().getIdentifier(item.getImageName(),"drawable",
                            "com.lumibao.expirydatetracker");
                }
            }
        }

        expiredTitle = Integer.toString(numberExpired) + " Items have expired!";
        if (numberExpired <= 0) {
            Log.i("~~~~", "returned null");
            return null;
        } else if (numberExpired == 1) {
            expiredTitle = "1 Item has expired!";
            expiredMessage = expiredItems.get(0) + " has expired.";
        } else if (numberExpired == 2) {
            expiredMessage = expiredItems.get(0) + " and " + expiredItems.get(1) + " have expired.";
        } else if (numberExpired > 2) {
            for (int index = 0; index < numberExpired; index++){

                if (index == numberExpired - 1) {
                    expiredMessage += " and " + expiredItems.get(index) + " have expired.";
                    break;
                }
                if (index == 0) {
                    expiredMessage = expiredItems.get(index);
                } else {
                    expiredMessage += ", " + expiredItems.get(index);
                }
            }
        }

        return new NotificationItem(expiredTitle, expiredMessage, numberExpired,expiredImage);
    }

    //~~~ EXPIRES TOMORROW ~~~
    public NotificationItem getExpiresTomorrow() {
        List<String> expiredItems = new ArrayList<>();
        String expiredTitle = "";
        String expiredMessage = "";
        int numberExpired = 0;
        int expiredImage = 0;

        for (Item item : itemList) {
            if (item.getDaysUntilExpired() == 0) {
                numberExpired++;
                expiredItems.add(item.getTitle());
                expiredImage = context.getResources().getIdentifier(item.getImageName(),"drawable",
                        "com.lumibao.expirydatetracker");
            }
        }

        expiredTitle = Integer.toString(numberExpired) + " Items expire tomorrow!";
        if (numberExpired <= 0) {
            return null;
        } else if (numberExpired == 1) {
            expiredTitle = "1 Item expires tomorrow!";
            expiredMessage = expiredItems.get(0) + " expires tomorrow.";
        } else if (numberExpired == 2) {
            expiredMessage = expiredItems.get(0) + " and " + expiredItems.get(1) + " expire tomorrow.";
        } else if (numberExpired > 2) {
            for (int index = 0; index < numberExpired; index++){

                if (index == numberExpired - 1) {
                    expiredMessage += " and " + expiredItems.get(index) + " expire tomorrow.";
                    break;
                }
                if (index == 0) {
                    expiredMessage = expiredItems.get(index);
                } else {
                    expiredMessage += ", " + expiredItems.get(index);
                }
            }
        }

        return new NotificationItem(expiredTitle, expiredMessage, numberExpired,expiredImage);
    }

    //~~~ EXPIRES SOON ~~~
    public NotificationItem getExpiresSoon() {
        List<String> expiredItems = new ArrayList<>();
        String expiredTitle = "";
        String expiredMessage = "";
        int numberExpired = 0;
        int expiredImage = 0;

        for (Item item : itemList) {
            if (item.getDaysUntilExpired() == 2) {
                numberExpired++;
                expiredItems.add(item.getTitle());
                expiredImage = context.getResources().getIdentifier(item.getImageName(),"drawable",
                        "com.lumibao.expirydatetracker");
            }
        }

        expiredTitle = Integer.toString(numberExpired) + " Items will expire in 3 days!";
        if (numberExpired <= 0) {
            return null;
        } else if (numberExpired == 1) {
            expiredTitle = "1 Item will expire in 3 days.";
            expiredMessage = expiredItems.get(0) + " will expire in 3 days.";
        } else if (numberExpired == 2) {
            expiredMessage = expiredItems.get(0) + " and " + expiredItems.get(1) + " will expire in 3 days.";
        } else if (numberExpired > 2) {
            for (int index = 0; index < numberExpired; index++){

                if (index == numberExpired - 1) {
                    expiredMessage += " and " + expiredItems.get(index) + " will expire in 3 days.";
                    break;
                }
                if (index == 0) {
                    expiredMessage = expiredItems.get(index);
                } else {
                    expiredMessage += ", " + expiredItems.get(index);
                }
            }
        }

        return new NotificationItem(expiredTitle, expiredMessage, numberExpired,expiredImage);
    }
}
