package com.lumibao.expirydatetracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lumibao.expirydatetracker.Item;

public class AddOrEditActivity extends AppCompatActivity {

    Intent intent;
    Item item;

    // AddOrEdit Views
    TextView entry_title_txt;
    ImageView item_img;
    DatePicker date_picker;
    Button cancel_btn;
    Button remove_btn;
    Button save_or_add_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lumibao.expirydatetracker.R.layout.add_or_edit_view);

        // Link views to activity
        entry_title_txt = (TextView) findViewById(R.id.enter_title_txt);
        item_img = (ImageView) findViewById(R.id.item_img);
        date_picker = findViewById(R.id.date_picker);

        // Cancel button
        cancel_btn = findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("~~~Test", "cancel button pressed");
                finish();
            }
        });
        remove_btn = findViewById(R.id.remove_btn);
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("~~~", "remove button pressed");
            }
        });
        save_or_add_btn = findViewById(R.id.save_or_add_btn);

        intent = getIntent();
        item = (Item)intent.getSerializableExtra("Item");
        if (item != null) {
            Log.i("~~~", "Item is not null");
            entry_title_txt.setText(item.getTitle());
            item_img.setImageResource(item.getImageID());
            save_or_add_btn.setText("Save");
        } else {
            // The user is creating a new item.


            remove_btn.setVisibility(View.GONE);
            save_or_add_btn.setText("Add");
        }
    }
}
