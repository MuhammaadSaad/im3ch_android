package com.adnan.tech.im3ch.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adnan.tech.im3ch.R;

import org.jetbrains.annotations.NotNull;

public class CustomerViewHolder extends RecyclerView.ViewHolder {



    public TextView tvName,time ;
    public TextView price ;
    public TextView Description ;
    public Button contact,chat ;
    public ImageView loc,serviceType,images ;

    public CustomerViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        time = itemView.findViewById(R.id.time);
        price = itemView.findViewById(R.id.price);
        Description = itemView.findViewById(R.id.Description);
        contact = itemView.findViewById(R.id.contact);
        chat = itemView.findViewById(R.id.chat);
        loc = itemView.findViewById(R.id.img_location);
        serviceType = itemView.findViewById(R.id.serviceType);
        images = itemView.findViewById(R.id.images);
    }
}