package com.adnan.tech.im3ch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.DialogClass;

public class SettingActivity extends AppCompatActivity {

    Context context;
    ImageView img_setting_more, img_profile;
    LinearLayout lnr_name, lnr_password, lnr_email, lnr_phone, lnr_sign_out;
    TextView tv_name, tv_password, tv_email, tv_phone, tv_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        new Anim().Entry(this);
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

            context = this;
            tv_name = findViewById(R.id.tv_name);
            tv_password = findViewById(R.id.tv_password);
            tv_email = findViewById(R.id.tv_email);
            tv_phone = findViewById(R.id.tv_phone);
            tv_sign_out = findViewById(R.id.tv_sign_out);

            lnr_name = findViewById(R.id.lnr_name);
            lnr_password = findViewById(R.id.lnr_password);
            lnr_email = findViewById(R.id.lnr_email);
            lnr_phone = findViewById(R.id.lnr_phone);
            lnr_sign_out = findViewById(R.id.lnr_sign_out);

            img_profile = findViewById(R.id.img_profile);
            img_setting_more = findViewById(R.id.img_setting_more);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void onClick() {
        try {
            img_setting_more.setOnClickListener(v -> {
                Intent intent = new Intent(this, ProfilePicActivity.class);
                startActivity(intent);
            });
            img_profile.setOnClickListener(v -> {
            });
            lnr_password.setOnClickListener(v -> {
                {
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = inflater.inflate(R.layout.dialog_change_password, null);
                    alertDialogBuilder.setView(view);
                    alertDialogBuilder.setCancelable(false);
                    AlertDialog dialog = alertDialogBuilder.create();

                    ImageView imgclose = view.findViewById(R.id.btnclose);
                    imgclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    final EditText et_old_pwd = view.findViewById(R.id.tv_old_pwd);
                    final EditText et_new_pwd = view.findViewById(R.id.tv_new_pwd);
                    final EditText et_new_confirm_pwd = view.findViewById(R.id.tv_new_confirm_pwd);
                    final Button btn_Change = view.findViewById(R.id.btn_Change);
                    btn_Change.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    dialog.show();
                }
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