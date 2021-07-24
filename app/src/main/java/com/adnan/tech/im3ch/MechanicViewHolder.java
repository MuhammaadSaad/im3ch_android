package com.adnan.tech.im3ch;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MechanicViewHolder extends RecyclerView.ViewHolder {



   public TextView tvName ;
   public TextView price ;
   public TextView Description ;
   public Button contact,chat ;

    public MechanicViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        price = itemView.findViewById(R.id.price);
        Description = itemView.findViewById(R.id.Description);
        contact = itemView.findViewById(R.id.contact);
        chat = itemView.findViewById(R.id.chat);
    }
}