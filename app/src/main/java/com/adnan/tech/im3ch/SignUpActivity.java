package com.adnan.tech.im3ch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import com.adnan.tech.im3ch.Util.ConstVar;
import com.adnan.tech.im3ch.Util.DialogClass;
import com.adnan.tech.im3ch.Util.ParamGetter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    Button btn_sign_up;
    TextView tv_type, tv_gender;
    EditText et_user_name, et_pwd, et_email, et_number;
    Context context;
    String Type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        try {
            Init();
            OnClickListener();
            Type=getIntent().getStringExtra(ConstVar.Type);
        } catch (Exception ex) {
            new DialogClass(this, "Exception", ex.getMessage());
        }
    }

    private void Init() {
        try {
            new Anim().Entry(this);
            btn_sign_up = findViewById(R.id.btn_sign_up);
            context=this;
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
                                OkHttpClient client = new OkHttpClient();
                                MediaType mediaType = MediaType.parse(" application/x-www-form-urlencoded");
                                ArrayList<ModelParams> params = new ArrayList<>();
                                params.add(new ModelParams("choice", Type));
                                params.add(new ModelParams("name1", et_user_name.getText().toString()));
                                params.add(new ModelParams("password1", et_pwd.getText().toString()));
                                params.add(new ModelParams("email1", et_email.getText().toString()));
                                params.add(new ModelParams("phone1", et_number.getText().toString()));
                                params.add(new ModelParams("gender1", "male"));
                                String parameters = ParamGetter.getValue(params);
                                Log.e("params",parameters);
                                //"?choice=Customer&name1=Local Business &password1=444444444&email1=dryera@fddd.com&phone1=44224&gender1=male
                                RequestBody body = RequestBody.create(mediaType, "choice=Customer&name1=Local Business&password1=444444444&email1=dryera@fddd.com&phone1=44224&gender1=male");
                                Request request = new Request.Builder()
                                        .url(new Api().URL + new Api().signup)
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
                                                if (!response.isSuccessful()) {

                                                    Log.e("test", response.body().toString());
                                                    String jsonData = null;
                                                    try {
                                                        jsonData = response.body().string();
                                                        JSONObject Jobject = new JSONObject(jsonData);
                                                        Log.e("error",Jobject.getString("message"));
                                                        Log.e("error",Jobject.getString("error"));
                                                    } catch (IOException | JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                } else {

                                                    // do something wih the result
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

    public void funSignUp(){
        try{

        }catch (Exception ex){

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