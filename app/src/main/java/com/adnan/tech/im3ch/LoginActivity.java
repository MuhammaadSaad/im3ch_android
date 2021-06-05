package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Model.ModelParams;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.Dialog_Loading;
import com.adnan.tech.im3ch.Util.MyPrefs;
import com.adnan.tech.im3ch.Util.ParamGetter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    Dialog_Loading loading;
    Button btn_login;
    EditText et_user_name, et_pwd, et_email;
    MyPrefs prefs;
    Context context;

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        try {
            Init();
            OnClickListener();
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            context = this;
            new Anim().Entry(this);
            prefs = new MyPrefs(this);

            btn_login = findViewById(R.id.btn_login);
            et_user_name = findViewById(R.id.et_user_name);
            et_pwd = findViewById(R.id.et_pwd);
            et_email = findViewById(R.id.et_email);
            loading = new Dialog_Loading(this);

        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void OnClickListener() {
        try {
            btn_login.setOnClickListener(this::onClick);
        } catch (
                Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }

    private void onClick(View v) {
        try {
            if (!(et_user_name.getText().toString().equals("") &&
                    et_pwd.getText().toString().equals("") &&
                    et_email.getText().toString().equals(""))) {
                if (isValidEmail(et_email.getText().toString())) {
                    loading.show();
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse(" application/x-www-form-urlencoded");

                    ArrayList<ModelParams> params = new ArrayList<>();
                    params.add(new ModelParams("name", et_user_name.getText().toString()));
                    params.add(new ModelParams("password", et_pwd.getText().toString()));
                    params.add(new ModelParams("email", et_email.getText().toString()));
                    String parameters = ParamGetter.getValue(params);

                    Log.e("test", "name=" + et_user_name.getText().toString() + "&password=" + et_pwd.getText().toString() + "&email=" + et_email.getText().toString());
                    RequestBody body = RequestBody.create(mediaType, parameters);//"name=sa&password=sa123456&email=sa@gmail.com"); //
                    String url = new Api().URL + new Api().login;
                    Request request = new Request.Builder()
                            .url(url)
                            .method("POST", body)
                            .addHeader("Content-Type", " application/x-www-form-urlencoded")
                            .build();
                    client.newCall(request).enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    loading.dismiss();
                                    new DialogClass(context, "Error", e.getMessage());
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Response response) {
                                    loading.dismiss();
                                    Log.e("test", response.message());
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    if (response.message().equalsIgnoreCase("OK")) {
                                        startActivity(intent);
                                    } else {
                                        new DialogClass(context, "Error", response.message());
                                    }
                                }
                            });
                } else {
                    Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }
}