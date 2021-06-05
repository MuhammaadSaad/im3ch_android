package com.adnan.tech.im3ch;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Model.ModelParams;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.BackgroundToast;
import com.adnan.tech.im3ch.Util.ConstVar;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.Dialog_Loading;
import com.adnan.tech.im3ch.Util.ParamGetter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    Dialog_Loading loading;
    Button btn_sign_up;
    TextView tv_type, tv_gender;
    EditText et_user_name, et_pwd, et_email, et_number;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try {
            Init();
            OnClickListener();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            new Anim().Entry(this);
            loading = new Dialog_Loading(this);
            btn_sign_up = findViewById(R.id.btn_sign_up);
            context = this;
            tv_type = findViewById(R.id.tv_type);
            tv_gender = findViewById(R.id.tv_gender);

            et_user_name = findViewById(R.id.et_user_name);
            et_pwd = findViewById(R.id.et_pwd);
            et_email = findViewById(R.id.et_email);
            et_number = findViewById(R.id.et_number);
            tv_type.setText(getIntent().getStringExtra(ConstVar.Type));

        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void OnClickListener() {
        try {
            tv_gender.setOnClickListener(v -> {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Select your gender");

                    String[] lst_gender = {"Male", "Female"};
                    builder.setItems(lst_gender, (dialog, which) -> tv_gender.setText(lst_gender[which]));

                    AlertDialog dialog = builder.create();
                    dialog.show();
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
            btn_sign_up.setOnClickListener(v -> {
                try {
                    if (!(et_user_name.getText().toString().equals("") &&
                            et_pwd.getText().toString().equals("") &&
                            et_email.getText().toString().equals("") &&
                            et_number.getText().toString().equals(""))) {
                        if (et_pwd.getText().toString().length() > 8) {
                            if (isValidEmail(et_email.getText().toString())) {
                                loading.show();
                                OkHttpClient client = new OkHttpClient();
                                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                                ArrayList<ModelParams> params = new ArrayList<>();
                                params.add(new ModelParams("choice", tv_type.getText().toString().toLowerCase()));
                                params.add(new ModelParams("name1", et_user_name.getText().toString().toLowerCase()));
                                params.add(new ModelParams("password1", et_pwd.getText().toString().toLowerCase()));
                                params.add(new ModelParams("email1", et_email.getText().toString().toLowerCase()));
                                params.add(new ModelParams("phone1", et_number.getText().toString().toLowerCase()));
                                params.add(new ModelParams("gender1", tv_gender.getText().toString().toLowerCase()));
                                String parameters = ParamGetter.getValue(params);
                                Log.e("params", parameters);
                                RequestBody body = RequestBody.create(mediaType,
                                        parameters);
                                Request request = new Request.Builder()
                                        .url(new Api().URL + new Api().signup)
                                        .method("POST", body)
                                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                        .build();
                                client.newCall(request).enqueue(
                                        new Callback() {
                                            @Override
                                            public void onFailure(Request request, IOException e) {
                                                loading.dismiss();
                                                new BackgroundToast().showDialog(context,
                                                        "Error",
                                                        e.getMessage());
                                                e.printStackTrace();
                                            }

                                            @Override
                                            public void onResponse(Response response) {
                                                try {
                                                    loading.dismiss();
                                                    Log.e("test", response.message());
                                                    if (response.message().equalsIgnoreCase("Created")) {
                                                        new BackgroundToast().showDialog(context,
                                                                "Message",
                                                                "Registered Successfully");
                                                        et_email.setText("");
                                                        et_pwd.setText("");
                                                        et_number.setText("");
                                                        et_user_name.setText("");
                                                        tv_gender.setText("");
                                                    } else {
                                                        new BackgroundToast().showDialog(context,
                                                                "Error",
                                                                "Wrong Credentials");
                                                    }
                                                } catch (Exception ex) {
                                                    new BackgroundToast().showDialog(context,
                                                            "Error",
                                                            ex.getMessage());
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Password length should be greater than 8", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    new DialogClass(this, "Exception", ex.getMessage());
                }
            });
        } catch (
                Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }

    }



    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}