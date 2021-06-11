package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.Dialog_Loading;
import com.adnan.tech.im3ch.Util.MyPrefs;

public class ProfilePicActivity extends AppCompatActivity {

    ImageView img_profile;
    Dialog_Loading loading;
    MyPrefs prefs;
    Context context;
    static int PICK_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic);
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
            loading = new Dialog_Loading(this);
            img_profile = findViewById(R.id.img_profile);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void ClickListener() {
        try {
            img_profile.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_FROM_GALLERY);
                } catch (Exception ex) {

                }
            });
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLERY) {
            try {
                img_profile.setImageURI(data.getData());
            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}