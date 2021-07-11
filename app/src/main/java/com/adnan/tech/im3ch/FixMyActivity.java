package com.adnan.tech.im3ch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adnan.tech.im3ch.Model.ModelAddress;
import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.BackgroundToast;
import com.adnan.tech.im3ch.Util.GSON_Module;
import com.adnan.tech.im3ch.Util.MyPrefs;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FixMyActivity extends AppCompatActivity {
    EditText et_location,et_make,et_model,et_year,et_budget,et_description;
    MyPrefs prefs;
    ImageView img_location, img_item;
    String address, lat_long;
    Button btn_submit;
    static int PICK_FROM_GALLERY = 1;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_my);
        context=this;
        prefs = new MyPrefs(this);
        new Anim().Entry(this);
        et_location = findViewById(R.id.et_location);
        et_make = findViewById(R.id.tv_car_makr);
        et_model = findViewById(R.id.tv_car_model);
        et_year = findViewById(R.id.tv_car_year);
        et_budget = findViewById(R.id.et_budget);
        et_description = findViewById(R.id.description);
        img_location = findViewById(R.id.img_location);
        btn_submit = findViewById(R.id.btn_Next);
        img_item = findViewById(R.id.img_item);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location,make,model,year,budget,description;
                location=et_location.getText().toString();
                make=et_make.getText().toString();
                model=et_model.getText().toString();
                year=et_year.getText().toString();
                budget=et_budget.getText().toString();
                description=et_description.getText().toString();
                if(!(location.isEmpty()&&make.isEmpty()&&model.isEmpty()&&year.isEmpty()&&budget.isEmpty()&&description.isEmpty())) {
                    OkHttpClient client = new OkHttpClient();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        jsonObject.put("make", make);
                        jsonObject.put("model", model);
                        jsonObject.put("year", year);
                        jsonObject.put("budget", budget);
                        jsonObject.put("latitude", "75.25");
                        jsonObject.put("longitude", "54.62");
                        jsonObject.put("description", description);
                        jsonObject.put("customerid", prefs.get_Val("id"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("dent_type", "indoor");
                        jsonObject.put("Time", timeStamp);
                        jsonObject.put("pics", "");
                        Log.e("test", jsonObject.toString());
                        // jsonObject.put("image", "suzuki");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                    Request request = new Request.Builder()
                            .url(new Api().URL + "car")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    client.newCall(request).enqueue(
                            new Callback() {
                                @Override
                                public void onFailure(Request request, IOException e) {
                                    //loading.dismiss();
                                    new BackgroundToast().showDialog(context,
                                            "Error",
                                            e.getMessage());
                                    e.printStackTrace();
                                }

                                @Override
                                public void onResponse(Response response) {
                                    try {
                                        //loading.dismiss();
                                        ResponseBody rebody = response.body();
                                        String responseb = rebody.string();

                                        Log.e("test", responseb);
                                        JSONObject json = new JSONObject(responseb);
                                        System.out.println(json.toString());
                                        String message = json.getString("message");
                                        if (message.equalsIgnoreCase("your request Submitted successfully")) {
                                            new BackgroundToast().showDialog(context,
                                                    "Message",
                                                    message);
                                        } else {
                                            new BackgroundToast().showDialog(context,
                                                    "Message",
                                                    message);
                                        }
                                    } catch (Exception ex) {
                                        new BackgroundToast().showDialog(context,
                                                "Error",
                                                ex.getMessage());
                                    }
                                }
                            });
                }
            }
        });
        img_item.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_FROM_GALLERY);
            } catch (Exception ex) {

            }
        });
        img_location.setOnClickListener(v -> {
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("address", address);
            intent.putExtra("lat_long", lat_long);
            startActivityForResult(intent, 2);
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra("val");
                GSON_Module gson = new GSON_Module();
                ArrayList<ModelAddress> lst_address = gson._get_address(val);
                address = lst_address.get(0).getAddress();
                lat_long = lst_address.get(0).getLat_long();
                et_location.setText(address);

            }

        }
        if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLERY) {
            try {
                img_item.setImageURI(data.getData());
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}