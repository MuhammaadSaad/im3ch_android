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
import com.adnan.tech.im3ch.Util.BackgroundToast;
import com.adnan.tech.im3ch.Util.ConstVar;
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
import com.adnan.tech.im3ch.Util.ParamGetter;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    Dialog_Loading loading;
    Button btn_sign_up;
    TextView tv_type, tv_gender;
    EditText et_user_name, et_pwd, et_email, et_number;
    Context context;
    MyPrefs prefs;
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
            loading = new Dialog_Loading(this);
            btn_sign_up = findViewById(R.id.btn_sign_up);
            context = this;
            tv_type = findViewById(R.id.tv_type);
            tv_gender = findViewById(R.id.tv_gender);
            prefs = new MyPrefs(this);
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
                                                    ResponseBody rebody = response.body();
                                                    String responseb = rebody.string();
                                                    Log.e("test",  responseb);// {"message":"data is saved successfully","data":{"_id":"60cc25b2f40fbb2e8c215ccb","choice":"mechanic","name1":"bello","password1":"123456789","phone1":"18937899","email1":"gft@gm.com","gender1":"male","tokens":[],"__v":0}}
                                                    JSONObject json = new JSONObject(responseb);
                                                    System.out.println(json.toString());
                                                    String message = json.getString("message");
                                                    if (message.equalsIgnoreCase("Created")) {
                                                        JSONObject data = json.getJSONObject("data");
                                                        prefs.put_Val("id",data.getString("_id"));
                                                        prefs.put_Val("choice",data.getString("choice"));
                                                        prefs.put_Val("name",data.getString("name1"));
                                                        prefs.put_Val("password",data.getString("password1"));
                                                        prefs.put_Val("email",data.getString("email1"));
                                                        prefs.put_Val("phone",data.getString("phone1"));
                                                        prefs.put_Val("gender",data.getString("gender1"));
                                                        new BackgroundToast().showDialog(context,
                                                                "Message",
                                                                "Registered Successfully");
                                                        Intent intent = new Intent(context, HomeActivity.class);
                                                        startActivity(intent);
                                                       /* prefs.put_Val("password", et_pwd.getText().toString());
                                                        prefs.put_Val("email", et_email.getText().toString());*/
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