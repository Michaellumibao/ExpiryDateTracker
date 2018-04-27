package com.lumibao.expirydatetracker;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddOrEditActivity extends AppCompatActivity {

    Intent intent;
    Item item;
    String imageName;
    int imageID;
    int index;
    boolean adding;
    String itemTitle;
    List<String[]> iconsList;

    // AddOrEdit Views
    TextInputEditText enter_title_txt;
    TextView choose_image_txt;
    ImageView item_img;
    DatePicker date_picker;
    Button remove_btn;
    Button save_or_add_btn;
    RecyclerView icons_list_view;
    IconsRecyclerViewAdapter iconsRecyclerViewAdapter;

    ImageButton meat_btn;
    ImageButton fruit_btn;
    ImageButton grains_btn;
    ImageButton dairy_btn;
    ImageButton misc_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.lumibao.expirydatetracker.R.layout.add_or_edit_view);

        iconsList = new ArrayList<>();

        // Back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Icon tab clicker
        meat_btn = findViewById(R.id.meat_btn);
        meat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unhighlightButtons();
                meat_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                populateMeat();
                iconsRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        fruit_btn = findViewById(R.id.fruit_btn);
        fruit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unhighlightButtons();
                fruit_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                populateFruit();
                iconsRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        grains_btn = findViewById(R.id.grain_btn);
        grains_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unhighlightButtons();
                grains_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                populateGrain();
                iconsRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        dairy_btn = findViewById(R.id.dairy_btn);
        dairy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unhighlightButtons();
                dairy_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                populateDairy();
                iconsRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        misc_btn = findViewById(R.id.misc_btn);
        misc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unhighlightButtons();
                misc_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                populateMisc();
                iconsRecyclerViewAdapter.notifyDataSetChanged();

            }
        });
        choose_image_txt = findViewById(R.id.choose_image_label);

        // Populate meat and highlight the button
        populateMeat();
        unhighlightButtons();
        meat_btn.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        iconsRecyclerViewAdapter = new IconsRecyclerViewAdapter(this, iconsList);
        icons_list_view = findViewById(R.id.icons_list_view);
        icons_list_view.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        icons_list_view.setAdapter(iconsRecyclerViewAdapter);

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
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.set(date_picker.getYear(), date_picker.getMonth(), date_picker.getDayOfMonth());
                boolean error = false;
                if (TextUtils.isEmpty(enter_title_txt.getText())) {
                    error = true;
                    enter_title_txt.setError("Please Enter a Title...");
                    enter_title_txt.requestFocus();
                } else {
                    if (adding) {
                        item = new Item(imageName, itemTitle, expiryDate);
                        intent.putExtra("Item", item);
                        setResult(2, intent);
                        finish();
                    } else {
                        item = new Item(imageName, itemTitle, expiryDate);
                        intent.putExtra("Item", item);
                        intent.putExtra("Index", index);
                        // resultCode 3 = saved
                        setResult(3, intent);
                        finish();
                    }
                }
            }
        });

        intent = getIntent();
        // -1 is used as a marker that no index was passed.
        index = intent.getIntExtra("Index", -1);
        item = (Item)intent.getSerializableExtra("Item");
        if (item != null) {
            itemTitle = item.getTitle();
            enter_title_txt.setText(itemTitle);
            imageName = item.getImageName();
            item_img.setImageResource(getResources().getIdentifier(imageName, "drawable", "com.lumibao.expirydatetracker"));
            save_or_add_btn.setText("Save");
            adding = false;
            date_picker.init(item.getExpiryDay().get(Calendar.YEAR), item.getExpiryDay().get(Calendar.MONTH),
                    item.getExpiryDay().get(Calendar.DATE),null);
        } else {
            // The user is creating a new item.
            remove_btn.setVisibility(View.GONE);
            save_or_add_btn.setText("Add");
            adding = true;
            itemTitle = getResources().getResourceEntryName(R.drawable.groceries);
            item_img.setImageResource(getResources().getIdentifier(itemTitle,"drawable", "com.lumibao.expirydatetracker"));
        }

    }

    // This function is called from the Icons adapter class
    public void updateImage(String imageName) {
        this.imageName = imageName;
        item_img.setImageResource(getResources().getIdentifier(imageName, "drawable",
                "com.lumibao.expirydatetracker"));
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void populateList() {
        iconsList.clear();
            Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.apple), resources.getResourceEntryName(R.drawable.bacon),
                resources.getResourceEntryName(R.drawable.baguette)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.beans), resources.getResourceEntryName(R.drawable.biscuit),
                resources.getResourceEntryName(R.drawable.bread_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.breakfast), resources.getResourceEntryName(R.drawable.butter),
                resources.getResourceEntryName(R.drawable.cake)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.can_2), resources.getResourceEntryName(R.drawable.cereals),
                resources.getResourceEntryName(R.drawable.cheese)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.chips), resources.getResourceEntryName(R.drawable.chocolate),
                resources.getResourceEntryName(R.drawable.coconut)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.cow), resources.getResourceEntryName(R.drawable.cracker),
                resources.getResourceEntryName(R.drawable.cupcake_2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.doughnut_1), resources.getResourceEntryName(R.drawable.doughnut_2),
                resources.getResourceEntryName(R.drawable.doughnut)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.eggs), resources.getResourceEntryName(R.drawable.fast_food),
                resources.getResourceEntryName(R.drawable.fish)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.fish2), resources.getResourceEntryName(R.drawable.flour),
                resources.getResourceEntryName(R.drawable.fries)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.groceries), resources.getResourceEntryName(R.drawable.ham),
                resources.getResourceEntryName(R.drawable.ham2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.hamburguer_1), resources.getResourceEntryName(R.drawable.honey),
                resources.getResourceEntryName(R.drawable.hot_sauce)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.ice_cream_13), resources.getResourceEntryName(R.drawable.jam_1),
                resources.getResourceEntryName(R.drawable.jam)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.jelly), resources.getResourceEntryName(R.drawable.jelly2),
                resources.getResourceEntryName(R.drawable.kebab_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.lasagne), resources.getResourceEntryName(R.drawable.lemon),
                resources.getResourceEntryName(R.drawable.lobster)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.meat_1), resources.getResourceEntryName(R.drawable.meat_2),
                resources.getResourceEntryName(R.drawable.meat)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.milk), resources.getResourceEntryName(R.drawable.noodles),
                resources.getResourceEntryName(R.drawable.octopus)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.pancakes_1), resources.getResourceEntryName(R.drawable.pasta),
                resources.getResourceEntryName(R.drawable.pastry)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.pickles), resources.getResourceEntryName(R.drawable.pie),
                resources.getResourceEntryName(R.drawable.pizza_2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.potatoes_1), resources.getResourceEntryName(R.drawable.pudding),
                resources.getResourceEntryName(R.drawable.ribs)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.ribs2), resources.getResourceEntryName(R.drawable.rice),
                resources.getResourceEntryName(R.drawable.rice2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.salad_1), resources.getResourceEntryName(R.drawable.salami),
                resources.getResourceEntryName(R.drawable.salmon)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.sausage), resources.getResourceEntryName(R.drawable.shrimp),
                resources.getResourceEntryName(R.drawable.spaguetti)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.spices), resources.getResourceEntryName(R.drawable.steak),
                resources.getResourceEntryName(R.drawable.steak2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.steak3), resources.getResourceEntryName(R.drawable.sushi_1),
                resources.getResourceEntryName(R.drawable.taco)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.tea), resources.getResourceEntryName(R.drawable.tuna),
                resources.getResourceEntryName(R.drawable.turkey)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.water_1), resources.getResourceEntryName(R.drawable.watermelon), resources.getResourceEntryName(R.drawable.z_fruits)});
    }

    public void populateMeat() {
        iconsList.clear();
        Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.bacon), resources.getResourceEntryName(R.drawable.eggs),
                resources.getResourceEntryName(R.drawable.fish)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.fish2), resources.getResourceEntryName(R.drawable.ham),
                resources.getResourceEntryName(R.drawable.ham2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.lobster), resources.getResourceEntryName(R.drawable.meat),
                resources.getResourceEntryName(R.drawable.meat_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.meat_2), resources.getResourceEntryName(R.drawable.octopus),
                resources.getResourceEntryName(R.drawable.ribs)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.ribs2), resources.getResourceEntryName(R.drawable.salami),
                resources.getResourceEntryName(R.drawable.salmon)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.sausage), resources.getResourceEntryName(R.drawable.shrimp),
                resources.getResourceEntryName(R.drawable.steak)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.steak2), resources.getResourceEntryName(R.drawable.steak3),
                resources.getResourceEntryName(R.drawable.tuna)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.tea), resources.getResourceEntryName(R.drawable.turkey),
                resources.getResourceEntryName(R.drawable.can_2)});
    }

    public void populateFruit() {
        iconsList.clear();
        Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.apple), resources.getResourceEntryName(R.drawable.beans),
                resources.getResourceEntryName(R.drawable.coconut)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.jam), resources.getResourceEntryName(R.drawable.jam_1),
                resources.getResourceEntryName(R.drawable.jelly)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.lemon), resources.getResourceEntryName(R.drawable.potatoes_1),
                resources.getResourceEntryName(R.drawable.salad_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.spices), resources.getResourceEntryName(R.drawable.watermelon),
                resources.getResourceEntryName(R.drawable.z_fruits)});

    }

    public void populateGrain() {
        iconsList.clear();
        Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.baguette), resources.getResourceEntryName(R.drawable.biscuit),
                resources.getResourceEntryName(R.drawable.cereals)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.chips), resources.getResourceEntryName(R.drawable.cracker),
                resources.getResourceEntryName(R.drawable.cupcake_2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.doughnut), resources.getResourceEntryName(R.drawable.doughnut_2),
                resources.getResourceEntryName(R.drawable.doughnut_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.flour), resources.getResourceEntryName(R.drawable.hamburguer_1),
                resources.getResourceEntryName(R.drawable.ice_cream_13)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.noodles), resources.getResourceEntryName(R.drawable.pancakes_1),
                resources.getResourceEntryName(R.drawable.pasta)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.pastry), resources.getResourceEntryName(R.drawable.pie),
                resources.getResourceEntryName(R.drawable.pizza_2)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.potatoes_1), resources.getResourceEntryName(R.drawable.pudding),
                resources.getResourceEntryName(R.drawable.rice)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.rice2), resources.getResourceEntryName(R.drawable.spaguetti),
                resources.getResourceEntryName(R.drawable.spices)});

    }

    public void populateDairy() {
        iconsList.clear();
        Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.biscuit), resources.getResourceEntryName(R.drawable.cake),
                resources.getResourceEntryName(R.drawable.cereals)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.cheese), resources.getResourceEntryName(R.drawable.cow),
                resources.getResourceEntryName(R.drawable.eggs)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.groceries), resources.getResourceEntryName(R.drawable.honey),
                resources.getResourceEntryName(R.drawable.ice_cream_13)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.lasagne), resources.getResourceEntryName(R.drawable.milk),
                resources.getResourceEntryName(R.drawable.pancakes_1)});

    }

    public void populateMisc() {
        iconsList.clear();
        Resources resources = getResources();
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.beans), resources.getResourceEntryName(R.drawable.breakfast),
                resources.getResourceEntryName(R.drawable.butter)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.cake), resources.getResourceEntryName(R.drawable.can_2),
                resources.getResourceEntryName(R.drawable.cereals)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.chocolate), resources.getResourceEntryName(R.drawable.cupcake_2),
                resources.getResourceEntryName(R.drawable.fast_food)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.groceries), resources.getResourceEntryName(R.drawable.hamburguer_1),
                resources.getResourceEntryName(R.drawable.honey)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.hot_sauce), resources.getResourceEntryName(R.drawable.jelly2),
                resources.getResourceEntryName(R.drawable.kebab_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.lasagne), resources.getResourceEntryName(R.drawable.noodles),
                resources.getResourceEntryName(R.drawable.pickles)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.pudding), resources.getResourceEntryName(R.drawable.spices),
                resources.getResourceEntryName(R.drawable.sushi_1)});
        iconsList.add(new String[]{resources.getResourceEntryName(R.drawable.taco), resources.getResourceEntryName(R.drawable.tea),
                resources.getResourceEntryName(R.drawable.water_1)});
    }

    public void unhighlightButtons() {
        meat_btn.setBackgroundColor(Color.TRANSPARENT);
        fruit_btn.setBackgroundColor(Color.TRANSPARENT);
        grains_btn.setBackgroundColor(Color.TRANSPARENT);
        dairy_btn.setBackgroundColor(Color.TRANSPARENT);
        misc_btn.setBackgroundColor(Color.TRANSPARENT);
    }
}
