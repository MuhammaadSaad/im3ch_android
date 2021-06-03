package com.adnan.tech.im3ch;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;

public class HighwayHandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highway_hand);
        new Anim().Entry(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}