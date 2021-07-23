package com.adnan.tech.im3ch.Adapters;


import android.content.Context;
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
        viewHolder.tvName.setText("Mechanic Name : "+mechanic.getName());
        viewHolder.tvLocation.setText("Location : "+mechanic.getLocation());
        viewHolder.Description.setText("Description : "+mechanic.getServices());
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
