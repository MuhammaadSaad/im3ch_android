package com.adnan.tech.im3ch;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Model.ModelAddress;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.GSON_Module;
import com.adnan.tech.im3ch.Util.MyPrefs;

import java.util.ArrayList;

public class FixMyActivity extends AppCompatActivity {
    EditText et_location;
    MyPrefs prefs;
    ImageView img_location, img_item;
    String address, lat_long;
    static int PICK_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_my);

        new Anim().Entry(this);
        prefs = new MyPrefs(this);
        et_location = findViewById(R.id.et_location);
        img_location = findViewById(R.id.img_location);
        img_item = findViewById(R.id.img_item);
        img_item.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FROM_GALLERY);
            } catch (Exception ex) {

            }
        });
        img_location.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("lat_long", lat_long);
            startActivityForResult(intent, 2);
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra("val");
                GSON_Module gson = new GSON_Module();
                ArrayList<ModelAddress> lst_address = gson._get_address(val);
                address = lst_address.get(0).getAddress();
                lat_long = lst_address.get(0).getLat_long();
                et_location.setText(address);

            }

        }
        if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLERY) {
            try {
                img_item.setImageURI(data.getData());
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}