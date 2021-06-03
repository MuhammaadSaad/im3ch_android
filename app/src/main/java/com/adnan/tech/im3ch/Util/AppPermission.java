package com.adnan.tech.im3ch.Util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class AppPermission {

    Context context;
    Activity activity;
    private int PERMISSION_ALL = 1;

    private String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public AppPermission(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        try {
            if (!hasPermissions()) {
                ActivityCompat.requestPermissions(activity,
                        PERMISSIONS, PERMISSION_ALL);
            }
        } catch (Exception ex) {
        }
    }

    public boolean hasPermissions() {
        if (context != null && PERMISSIONS != null) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
