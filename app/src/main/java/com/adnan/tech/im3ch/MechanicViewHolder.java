package com.adnan.tech.im3ch;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MechanicViewHolder extends RecyclerView.ViewHolder {



   public TextView tvName ;
   public TextView tvLocation ;
   public TextView Description ;

    public MechanicViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvLocation = itemView.findViewById(R.id.tvLocation);
        Description = itemView.findViewById(R.id.Description);
    }
}