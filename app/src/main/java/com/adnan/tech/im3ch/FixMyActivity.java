package com.adnan.tech.im3ch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.ConstVar;
import com.adnan.tech.im3ch.Util.MyPrefs;

public class FixMyActivity extends AppCompatActivity {
    EditText et_location;
    MyPrefs prefs;
    ImageView img_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_my);

        new Anim().Entry(this);
        prefs = new MyPrefs(this);
        et_location = findViewById(R.id.et_location);
        img_location = findViewById(R.id.img_location);
        img_location.setOnClickListener(v -> {
            startActivity(new Intent(this, MapActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }

    @Override
    protected void onResume() {
        if (!prefs.get_Val(ConstVar.pref_Address).equalsIgnoreCase("")) {
            et_location.setText(prefs.get_Val(ConstVar.pref_Address));
        }
        super.onResume();
    }
}