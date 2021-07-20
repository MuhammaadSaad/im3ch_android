package com.adnan.tech.im3ch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.adnan.tech.im3ch.Adapters.MechanicViewAdapter;
import com.adnan.tech.im3ch.Model.Mechanic;
import com.adnan.tech.im3ch.Util.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class FindMechanicActivity extends AppCompatActivity {
    RecyclerView listView;
    List<Mechanic> mechanics;
    MechanicViewAdapter mechanicViewAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mechanic);
        mechanics=new ArrayList<>();
        context=this;

        listView=findViewById(R.id.listView);
        FetchMechs();

        mechanicViewAdapter=new MechanicViewAdapter(context, mechanics);
        listView.setAdapter(mechanicViewAdapter);
    }
    void FetchMechs(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(new Api().URL+"MechReq")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response)  {
                try {
                    //loading.dismiss();
                    ResponseBody rebody = response.body();
                    String responseb = rebody.string();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          mechanics.addAll(Arrays.asList(gson.fromJson(responseb, Mechanic[].class)));
                                          mechanicViewAdapter=new MechanicViewAdapter(context, mechanics);
                                          listView.setAdapter(mechanicViewAdapter);
                                      }
                                  }
                    );

                    //Mechanic data = new Gson().fromJson(responseb, Mechanic.class);
                    //mechanicViewAdapter=new MechanicViewAdapter(context, mechanics);

                    //mechanicViewAdapter.notifyDataSetChanged();



                }catch (Exception ex){
                    Log.e("ex",ex.getMessage());                }
            }
        });
    }
}