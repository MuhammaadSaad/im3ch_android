package com.adnan.tech.im3ch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.adnan.tech.im3ch.Util.Anim;

public class IFixRideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_fix_ride);
        new Anim().Entry(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}