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

import java.io.IOException;
import java.util.ArrayList;

public class FixMyActivity extends AppCompatActivity {
    EditText et_location;
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

        new Anim().Entry(this);
        prefs = new MyPrefs(this);
        et_location = findViewById(R.id.et_location);
        img_location = findViewById(R.id.img_location);
        btn_submit = findViewById(R.id.btn_Next);
        img_item = findViewById(R.id.img_item);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                JsonObject jsonObject=new JsonObject();
                jsonObject.addProperty("make","suzuki" );
                RequestBody body = RequestBody.create(mediaType, "{\r\n    \"make\":\"suzuki\",\r\n     \"model\":\"202020\",\r\n      \"year\":\"2019\",\r\n       \"budget\":\"250\",\r\n        \"latitude\":\"75.35\",\r\n         \"longitude\":\"142.52\",\r\n          \"description\":\"suzuki\"\r\n}");
                Request request = new Request.Builder()
                        .url(new Api().URL +"car")
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

                                    Log.e("test",  responseb);
                                    if (response.message().equalsIgnoreCase("Created")) {
                                        new BackgroundToast().showDialog(context,
                                                "Message",
                                                "Inserted Successfully");
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