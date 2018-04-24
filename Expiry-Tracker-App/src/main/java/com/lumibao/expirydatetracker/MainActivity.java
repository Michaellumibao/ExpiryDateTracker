package com.lumibao.expirydatetracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView item_list;
    private List<Item> itemList;
    private RecyclerView item_list_view;
    private RecyclerViewAdapter itemAdapter;
//    RelativeLayout[] meatItems = new RelativeLayout[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        /*
        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.apple, "Apple", -1));
        itemList.add(new Item(R.drawable.apple, "Apple", -1));
        itemList.add(new Item(R.drawable.apple, "Apple", -1));
        itemList.add(new Item(R.drawable.avocado, "Avocado", 0));
        itemList.add(new Item(R.drawable.burger, "Burger", 1));
        itemList.add(new Item(R.drawable.burger2, "Chicken Leg", 4));
        itemList.add(new Item(R.drawable.donut, "Donute", 4));
        itemList.add(new Item(R.drawable.egg, "Egg", 4));
        itemList.add(new Item(R.drawable.fish, "Fish", 5));
        itemList.add(new Item(R.drawable.pizza, "Pizza", 10));
        itemList.add(new Item(R.drawable.pumpkin, "Pumpkins", 23));
        itemList.add(new Item(R.drawable.watermelon, "Watermelon", 25));
        itemList.add(new Item(R.drawable.watermelon, "Watermelon", 25));
        */

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
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
