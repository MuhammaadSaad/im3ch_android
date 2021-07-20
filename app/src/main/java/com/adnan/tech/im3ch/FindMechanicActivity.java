package com.adnan.tech.im3ch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.adnan.tech.im3ch.Adapters.MechanicViewAdapter;
import com.adnan.tech.im3ch.Model.Mechanic;

import java.util.ArrayList;

public class FindMechanicActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Mechanic> mechanics;
    MechanicViewAdapter mechanicViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_mechanic);
        mechanics=new ArrayList<>();

        listView=findViewById(R.id.listView);
        Mechanic mechanic=new Mechanic();
        mechanic.setName("Ali");
        mechanic.setLocation("Islamabad");
        mechanics.add(mechanic);


        mechanicViewAdapter=new MechanicViewAdapter(FindMechanicActivity.this, mechanics);
        listView.setAdapter(mechanicViewAdapter);

    }
}