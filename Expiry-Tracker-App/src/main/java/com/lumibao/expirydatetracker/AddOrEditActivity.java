package com.lumibao.expirydatetracker;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddOrEditActivity extends AppCompatActivity {

    Intent intent;
    Item item;
    int imageID;
    int index;
    boolean adding;
    String itemTitle;

    // AddOrEdit Views
    TextInputEditText enter_title_txt;
    ImageView item_img;
    DatePicker date_picker;
    Button cancel_btn;
    Button remove_btn;
    Button save_or_add_btn;
    Button choose_image_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lumibao.expirydatetracker.R.layout.add_or_edit_view);

        //Dismiss keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Link views to activity
        enter_title_txt = (TextInputEditText) findViewById(R.id.enter_title_txt);
        enter_title_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                itemTitle = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        item_img = (ImageView) findViewById(R.id.item_img);
        date_picker = findViewById(R.id.date_picker);

        // Cancel button
        cancel_btn = findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // Remove button
        remove_btn = findViewById(R.id.remove_btn);
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("Index", index);
                // resultCode 1 = removed
                setResult(1, intent);
                finish();
            }
        });

        // Save / add button
        save_or_add_btn = findViewById(R.id.save_or_add_btn);
        save_or_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;
                if (TextUtils.isEmpty(enter_title_txt.getText())){
                    error = true;
                    enter_title_txt.setError("Please Enter a Title...");
                } else {
                    if (adding) {
                        item = new Item(imageID, itemTitle, 0);
                        intent.putExtra("Item", item);
                        setResult(2, intent);
                        finish();
                    } else {
                        item = new Item(imageID, itemTitle, 0);
                        intent.putExtra("Item", item);
                        intent.putExtra("Index", index);
                        // resultCode 3 = saved
                        setResult(3, intent);
                        finish();
                    }
                }
            }
        });

        // Choose image button
        choose_image_btn = findViewById(R.id.choose_image_btn);
        choose_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageID = R.drawable.chicken_leg;
                item_img.setImageResource(imageID);
            }
        });

        intent = getIntent();
        // -1 is used as a marker that no index was passed.
        index = intent.getIntExtra("Index", -1);
        item = (Item)intent.getSerializableExtra("Item");
        if (item != null) {
            itemTitle = item.getTitle();
            enter_title_txt.setText(itemTitle);
            imageID = item.getImageID();
            item_img.setImageResource(imageID);
            save_or_add_btn.setText("Save");
            adding = false;
        } else {
            // The user is creating a new item.

            remove_btn.setVisibility(View.GONE);
            save_or_add_btn.setText("Add");
            adding = true;
        }
    }
}
