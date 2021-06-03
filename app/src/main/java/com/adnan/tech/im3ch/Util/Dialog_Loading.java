package com.adnan.tech.im3ch.Util;

import android.app.Dialog;
import android.content.Context;
import android.widget.ProgressBar;


import com.adnan.tech.im3ch.R;
import com.github.ybq.android.spinkit.style.FadingCircle;

public class Dialog_Loading {
    private ProgressBar progressBar;
    private Dialog dialog_loading;

    public Dialog_Loading(Context context) {

        dialog_loading = new Dialog(context);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.dialog_loading);
        progressBar = dialog_loading.findViewById(R.id.spin_kit);

        FadingCircle doubleBounce = new FadingCircle();
        progressBar.setIndeterminateDrawable(doubleBounce);
    }

    public void show() {
        dialog_loading.show();
    }

    public void dismiss() {
        dialog_loading.dismiss();
    }
}
