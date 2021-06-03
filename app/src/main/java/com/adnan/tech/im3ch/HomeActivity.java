package com.adnan.tech.im3ch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.DialogClass;

public class HomeActivity extends AppCompatActivity {
    ImageView img_setting, btn_fix_my_ride, btn_i_fix_ride, btn_highway_hand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        try {
            Init();
            onClick();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            new Anim().Entry(this);
            btn_fix_my_ride = findViewById(R.id.btn_fix_my_ride);
            btn_i_fix_ride = findViewById(R.id.btn_i_fix_ride);
            btn_highway_hand = findViewById(R.id.btn_highway_hand);
            img_setting = findViewById(R.id.img_setting);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void onClick() {
        try {

            btn_fix_my_ride.setOnClickListener(v -> {
                Intent intent = new Intent(this, FixMyActivity.class);
                startActivity(intent);
            });
            btn_i_fix_ride.setOnClickListener(v -> {
                Intent intent = new Intent(this, IFixRideActivity.class);
                startActivity(intent);
            });
            btn_highway_hand.setOnClickListener(v -> {
                Intent intent = new Intent(this, HighwayHandActivity.class);
                startActivity(intent);
            });
            img_setting.setOnClickListener(v -> {
                Intent intent = new Intent(this, SettingActivity.class);
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