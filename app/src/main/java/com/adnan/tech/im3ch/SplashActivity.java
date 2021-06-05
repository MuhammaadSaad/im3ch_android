package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.MyPrefs;

public class SplashActivity extends AppCompatActivity {
    MyPrefs prefs;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = new MyPrefs(this);
        try {
            Thread mSplashThread = new Thread() {
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(3000);
                        }
                    } catch (InterruptedException ex) {
                        ex.fillInStackTrace();
                    }
                    try {
                        if (!prefs.get_Val("email").equalsIgnoreCase("")) {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                        finish();
                    } catch (Exception ex) {
                        new DialogClass(context, "Exception", ex.getMessage());
                    }
                }
            };
            mSplashThread.start();
        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }
}