package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.MyPrefs;

public class HighwayHandActivity extends AppCompatActivity {
    MyPrefs prefs;
    Context context;
    LinearLayout lnr_fuel, lnr_tires, lnr_jump_start, lnr_towing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highway_hand);
        try {
            Init();
            ClickListener();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }

    }

    private void Init() {
        try {
            context = this;
            new Anim().Entry(this);
            prefs = new MyPrefs(this);
            lnr_fuel = findViewById(R.id.lnr_fuel);
            lnr_tires = findViewById(R.id.lnr_tires);
            lnr_jump_start = findViewById(R.id.lnr_jump_start);
            lnr_towing = findViewById(R.id.lnr_towing);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        try {
            lnr_fuel.setOnClickListener(v -> {
                Intent intent = new Intent(this, HighwayHandInfoActivity.class);
                intent.putExtra("type", "fuel");
                startActivity(intent);
            });

            lnr_tires.setOnClickListener(v -> {
                Intent intent = new Intent(this, HighwayHandInfoActivity.class);
                intent.putExtra("type", "tires");
                startActivity(intent);
            });

            lnr_towing.setOnClickListener(v -> {
                Intent intent = new Intent(this, HighwayHandInfoActivity.class);
                intent.putExtra("type", "towing");
                startActivity(intent);
            });

            lnr_jump_start.setOnClickListener(v -> {
                Intent intent = new Intent(this, HighwayHandInfoActivity.class);
                intent.putExtra("type", "jump start");
                startActivity(intent);
            });

        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}