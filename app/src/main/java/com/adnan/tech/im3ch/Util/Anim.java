package com.adnan.tech.im3ch.Util;

import android.app.Activity;

import com.adnan.tech.im3ch.R;


public class Anim {
    public void Entry(Activity activity) {
        activity.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    public void Back(Activity activity) {
        activity.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }
}
