package com.adnan.tech.im3ch.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adnan.tech.im3ch.MechanicViewHolder;
import com.adnan.tech.im3ch.Model.Mechanic;
import com.adnan.tech.im3ch.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MechanicViewAdapter  extends RecyclerView.Adapter<MechanicViewHolder>  {

    Context context;
    List<Mechanic> mechanics;

    public MechanicViewAdapter(Context context, List<Mechanic> mechanics) {
        this.context = context;
        this.mechanics = mechanics;
    }


    @NonNull
    @NotNull
    @Override
    public MechanicViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mechanic_view, parent, false);
        MechanicViewHolder viewHolder = new MechanicViewHolder(view);//.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MechanicViewHolder viewHolder, int position) {


        final Mechanic mechanic = (Mechanic) this.getItem(position);

            viewHolder.tvName.setText("Mechanic Name : " + mechanic.getName());
            viewHolder.price.setText("Price : Rs. " + mechanic.getPrice());
            viewHolder.Description.setText("Description : " + mechanic.getServices());

        viewHolder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://api.whatsapp.com/send?phone="+mechanic.getPhone();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });

        viewHolder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://wa.me/
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+mechanic.getPhone()));//change the number
                context.startActivity(callIntent);
            }
        });
    }

    Mechanic getItem(int i){
        return mechanics.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        Log.e("test",mechanics.size()+"");
        return mechanics.size();
    }


    public void updateModels(List<Mechanic> newModels) {
        mechanics.clear();
        mechanics.addAll(newModels);

        notifyDataSetChanged();
        //mechanics.notify();// notifyDataSetChaged();
    }
}
