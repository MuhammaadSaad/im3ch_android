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

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.Dialog_Loading;
import com.adnan.tech.im3ch.Util.MyPrefs;
import com.android.volley.RequestQueue;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


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

    public void funLogin() {
        /*try {
            StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType,
                    "choice=mechenic&name1=moh&password1=12345&email1=Moh%40Mail.Com&phone1=836543645&gender1=male");
            Request request = new Request.Builder()
                    .url("http://192.168.1.108:8080/signup/")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    //.addHeader("Cookie", "jwtoken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MGFiOWQ4NmNiM2UyZjBjOTA5MGExOWMiLCJpYXQiOjE2MjIyNzA5MDB9.tV9x5ilB3OfcLBMlwwUbIK2gfmCfMv_bWjTV5ElRV1c")
                    .build();
            Response response = client.newCall(request).execute();
            Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }*/
        try {
            StrictMode.ThreadPolicy policy = new   StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse(" application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "name=sa&password=sa123456&email=sa@gmail.com");
            Request request = new Request.Builder()
                    .url("http://192.168.0.149:8080/login")
                    .method("POST", body)
                    .addHeader("Content-Type", " application/x-www-form-urlencoded")
                    .build();
            client.newCall(request).enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Response response){
                            Log.e("test",response.message());
                            if (!response.isSuccessful()) {

                            } else {

                                // do something wih the result
                            }
                        }
                    });

            /*
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            ArrayList<ModelParams> params = new ArrayList<>();
            String url = "http://192.168.0.149:8080/signup/";

            //choice=mechenic&name1=moh&password1=12345&email1=Moh%40Mail.Com&phone1=836543645&gender1=male
            /*ArrayList<ModelContact> lst_contact = new ArrayList<>();
            lst_contact.add(new ModelContact("mechenic","moh","12345","abc@gmail.com","836543645","male"));
            JsonObject parent = new JsonObject();
            JsonArray jsonArray = new Gson().toJsonTree(lst_contact).getAsJsonArray();
            parent.add("Contact", jsonArray);

            String vals_1 = "choice=mechenic&name1=moh&password1=12345&email1=Moh%40Mail.Com&phone1=836543645&gender1=male";//parent.toString();

            loading = new Dialog_Loading(this);
            loading.show();
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST,
                    url,
                    res -> {
                        try {
                            loading.dismiss();
                            new DialogClass(context, "Exception", res);
                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());
                        }
                    },
                    error -> {
                        try {
                            loading.dismiss();
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());

                        }
                    }) {
                @Override
                public byte[] getBody() {
                    return vals_1.getBytes();
                }

                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

            };
            queue.add(sr);*/
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }


/*
    private void funLogin() {
        try {
            //RequestBody body = RequestBody.create(mediaType, "choice=mechenic&name1=moh&password1=12345&email1=Moh%40Mail.Com&phone1=836543645&gender1=male");
            loading.show();

            String url = prefs.get_Val(ConstVar.url) + ConstVar.url_login;

            JsonObject parent = new JsonObject();
            JsonArray jsonArray = new Gson().toJsonTree(lst_contact).getAsJsonArray();
            parent.add("Contact", jsonArray);
            String vals_1 = parent.toString();
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest sr = new StringRequest(Request.Method.POST,
                    url,
                    res -> {
                        try {
                            loading.dismiss();
                            res = res.trim().replace("\"", "");
                            if (res.equals("1")) {
                                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Unable to save, some thing went wrong", Toast.LENGTH_SHORT).show();
                            }
                            GetContacts();
                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());
                        }
                    },
                    error -> {
                        try {
                            loading.dismiss();
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            loading.dismiss();
                            new DialogClass(context, "Exception", ex.getMessage());

                        }
                    }) {
                @Override
                public byte[] getBody() {
                    return vals_1.getBytes();
                }

                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

            };
            queue.add(sr);


        } catch (Exception ex) {
            new DialogClass(context, "Exception", ex.getMessage());
        }
    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }

    private void onClick(View v) {
        try {
            //funLogin();
            if (!(et_user_name.getText().toString().equals("") &&
                    et_pwd.getText().toString().equals("") &&
                    et_email.getText().toString().equals(""))) {
                if (isValidEmail(et_email.getText().toString())) {

                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse(" application/x-www-form-urlencoded");
                    Log.e("test", "name=" + et_user_name.getText().toString() + "&password=" + et_pwd.getText().toString() + "&email=" + et_email.getText().toString());
                    RequestBody body = RequestBody.create(mediaType, "name=" + et_user_name.getText().toString() + "&password=" + et_pwd.getText().toString() + "&email=" + et_email.getText().toString() + "");//"name=sa&password=sa123456&email=sa@gmail.com"); //
                    Request request = new Request.Builder()
                            .url(new Api().URL + new Api().login)
                            .method("POST", body)
                            .addHeader("Content-Type", " application/x-www-form-urlencoded")
                            .build();
                    client.newCall(request).enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Response response) {
                                    String s = response.toString();
                                    s.toLowerCase();
                                    //abcdd
                                    Log.e("test", response.message());
                                    Intent intent = new Intent(context, HomeActivity.class);
                                    startActivity(intent);
                                    if (!response.isSuccessful()) {
                                         intent = new Intent(context, HomeActivity.class);
                                        startActivity(intent);
                                    } else {

                                        // do something wih the result
                                    }
                                }
                            });

                } else {
                    Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
            /*Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);*/
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }
}