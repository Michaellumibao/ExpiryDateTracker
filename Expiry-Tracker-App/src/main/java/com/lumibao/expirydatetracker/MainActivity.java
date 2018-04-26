package com.lumibao.expirydatetracker;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private ListView item_list;
    private List<Item> itemList;
    private RecyclerView item_list_view;
    private RecyclerViewAdapter itemAdapter;
//    RelativeLayout[] meatItems = new RelativeLayout[5];

    // Flag for notifications
    private boolean allowNotifications;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        // LOAD DATA
        loadData();

        for (Item item: itemList) {
            item.recalculateDaysUntilExpired();
        }

        Log.i("~~~allow", Boolean.toString(allowNotifications));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");

        itemAdapter = new RecyclerViewAdapter(this, itemList);
        item_list_view = (RecyclerView) findViewById(R.id.item_list_view);
        item_list_view.setLayoutManager(new LinearLayoutManager(this));
        item_list_view.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener(new RecyclerViewAdapter.ClickListener() {

            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(MainActivity.this, AddOrEditActivity.class);
                intent.putExtra("Item", itemList.get(position));
                intent.putExtra("Index", position);
                startActivityForResult(intent,0);
            }
        });
        // Initiate adapter
        //itemAdapter = new ItemAdapter(getApplicationContext(),itemList);
//        item_list.setAdapter(itemAdapter);
/*        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddOrEditActivity.class);
                intent.putExtra("Item", itemList.get(position));
                intent.putExtra("Index", position);
                startActivityForResult(intent,0);
            }
        });*/
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddOrEditActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if (resultCode == 0) {
            // Item is saved

        }
        else if (resultCode ==  1) {
            // result code 1 = removed
            int index = data.getIntExtra("Index", -1);
            if (index != -1) {
                itemList.remove(index);
                itemAdapter.notifyDataSetChanged();
            }
            Log.i("~~~request code", Integer.toString(requestCode));
            Log.i("~~~result code", Integer.toString(resultCode));
            Log.i("~~~Intent position", Integer.toString(data.getIntExtra("Index", -1)));
        } else if (resultCode == 2) {
            Item item = (Item)data.getSerializableExtra("Item");
            Log.i("~~~in 2?", "huh");

            if (item != null) {
                Log.i("~~~Huh?", "huh");
                itemList.add(item);
                itemAdapter.notifyDataSetChanged();
            }
        } else if (resultCode == 3) {
            Item item = (Item)data.getSerializableExtra("Item");
            int index = data.getIntExtra("Index", -1);
            if (index != -1) {
                itemList.set(index, item);
                itemAdapter.notifyDataSetChanged();
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("items data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString("items", json);
        editor.apply();

        // Get notifications preferences
        SharedPreferences notificationPreferences = getSharedPreferences("notification preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor notificationEditor = notificationPreferences.edit();
        notificationEditor.putBoolean("allowNotifications", allowNotifications);
        notificationEditor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("items data", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        itemList = gson.fromJson(json, type);

        if (itemList == null) {
            itemList = new ArrayList<>();
        }

        SharedPreferences notificationPreferences = getSharedPreferences("notification preferences", Context.MODE_PRIVATE);
        allowNotifications = notificationPreferences.getBoolean("allowNotifications", true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.notification_settings).setChecked(allowNotifications);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.notification_settings:
                allowNotifications = !allowNotifications;
                item.setChecked(allowNotifications);

                if (allowNotifications) {
                    setNotifications();
                } else {
                    disableNotifications();
                }
                return true;
            case R.id.action_credits:
                startActivity(new Intent(context, com.lumibao.expirydatetracker.CreditsActivity.class));

            default:
                return false;
        }
    }

    private void setNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.getInstance().YEAR, Calendar.getInstance().MONTH, Calendar.getInstance().DATE,
                18,16, 20);
        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);

        // Store the item list as JSON file using GSON
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        intent.putExtra("itemList", json);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void disableNotifications() {
        Intent intent = new Intent(getApplicationContext(), NotificationReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}

