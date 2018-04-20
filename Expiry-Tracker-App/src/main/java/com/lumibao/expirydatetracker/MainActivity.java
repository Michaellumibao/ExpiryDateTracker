package com.lumibao.expirydatetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView item_list;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
//    RelativeLayout[] meatItems = new RelativeLayout[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item_list = (ListView) findViewById(R.id.item_list);

        itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leasfasfasfasfasfaasfg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg", -1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg2", 1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg2", 1));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg3", 4));
        itemList.add(new Item(R.drawable.chicken_leg, "Chicken Leg3", 4));


        // Initiate adapter
        itemAdapter = new ItemAdapter(getApplicationContext(),itemList);
        item_list.setAdapter(itemAdapter);
        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddOrEditActivity.class);
                intent.putExtra("Item", itemList.get(position));
                startActivity(intent);
            }
        });
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddOrEditActivity.class));
            }
        });

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
