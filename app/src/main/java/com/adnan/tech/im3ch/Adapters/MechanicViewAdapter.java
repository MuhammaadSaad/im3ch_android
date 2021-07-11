package com.adnan.tech.im3ch.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adnan.tech.im3ch.Model.Mechanic;
import com.adnan.tech.im3ch.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MechanicViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<Mechanic> mechanics;

    public MechanicViewAdapter(Context context, ArrayList<Mechanic> mechanics) {
        this.context = context;
        this.mechanics = mechanics;
    }


    @Override
    public int getCount() {
        return mechanics.size();
    }

    @Override
    public Object getItem(int i) {
        return mechanics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.mechanic_view, viewGroup, false);
        }

        final Mechanic mechanic = (Mechanic) this.getItem(i);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvLocation = view.findViewById(R.id.tvLocation);


        tvName.setText("Mechanic Name : "+mechanic.getName());
        tvLocation.setText("Location : "+mechanic.getLocation());

        return view;
    }


}
