package com.adnan.tech.im3ch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.adnan.tech.im3ch.Util.Anim;
import com.adnan.tech.im3ch.Util.Api;
import com.adnan.tech.im3ch.Util.BackgroundToast;
import com.adnan.tech.im3ch.Util.MyPrefs;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class IFixRideActivity extends AppCompatActivity {
    EditText et_discr, et_time, et_budget;
    RadioButton rd_indoor,outdoor;
    Button btn_done;
    MyPrefs prefs;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i_fix_ride);
        context=this;
        new Anim().Entry(this);
        prefs = new MyPrefs(this);
        btn_done=findViewById(R.id.btn_Next);
        outdoor=findViewById(R.id.our_side);
        rd_indoor=findViewById(R.id.in_side);
        et_discr=findViewById(R.id.tv_discription);
        et_budget=findViewById(R.id.tv_budget);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String discr,  budget,rdSel;
                discr = et_discr.getText().toString();

                budget = et_budget.getText().toString();
                if(rd_indoor.isChecked() && !outdoor.isChecked()){
                    rdSel="In Door";
                }else if(!rd_indoor.isChecked() && outdoor.isChecked()){
                    rdSel="Out Door";
                }else{
                    Toast.makeText(context,"Please Select Dent Type You can repair",Toast.LENGTH_SHORT).show();
                    rdSel="";
                }
                if(!(discr.isEmpty() && budget.isEmpty() && rdSel.isEmpty())){
                    OkHttpClient client = new OkHttpClient();
                    MediaType mediaType = MediaType.parse("application/json");
                    JSONObject jsonObject = new JSONObject();
                    try {
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                        jsonObject.put("mechenicId",prefs.get_Val("id") );
                        jsonObject.put("name", prefs.get_Val("name"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("phone", prefs.get_Val("phone"));//"60cc25b2f40fbb2e8c215ccb"
                        jsonObject.put("description", discr);
                        jsonObject.put("price", budget);
                        jsonObject.put("dent_type", rdSel);
                        jsonObject.put("Time", timeStamp);
                        jsonObject.put("pics", "");
                        Log.e("test", jsonObject.toString());
                        // jsonObject.put("image", "suzuki");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(mediaType, jsonObject.toString());
                    Request request = new Request.Builder()
                            .url(new Api().URL + "/customer_request")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            new BackgroundToast().showDialog(context,
                                    "Error",
                                    e.getMessage());
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            try {
                                //loading.dismiss();
                                ResponseBody rebody = response.body();
                                String responseb = rebody.string();

                                Log.e("test", responseb);
                                JSONObject json = new JSONObject(responseb);
                                System.out.println(json.toString());
                                String message = json.getString("message");
                                if (message.equalsIgnoreCase("your are ready to go for fix the car")) {
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
                }else{
                    Toast.makeText(context,"Please Enter Values",Toast.LENGTH_SHORT).show();
                }
                //
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Anim().Back(this);
    }
}