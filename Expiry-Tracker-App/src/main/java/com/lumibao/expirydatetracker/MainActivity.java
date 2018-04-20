package com.lumibao.expirydatetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
                intent.putExtra("Index", position);
                startActivityForResult(intent,0);
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
        } else if (resultCode == 3) {
            Item item = (Item)data.getSerializableExtra("Item");
            int index = data.getIntExtra("Index", -1);
            if (index != -1) {
                itemList.set(index, item);
                itemAdapter.notifyDataSetChanged();
            }
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
