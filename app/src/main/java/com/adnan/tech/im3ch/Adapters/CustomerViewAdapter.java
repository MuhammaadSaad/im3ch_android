package com.adnan.tech.im3ch.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adnan.tech.im3ch.MapActivity;
import com.adnan.tech.im3ch.MechanicViewHolder;
import com.adnan.tech.im3ch.Model.Customer;
import com.adnan.tech.im3ch.Model.Mechanic;
import com.adnan.tech.im3ch.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomerViewAdapter extends RecyclerView.Adapter<CustomerViewHolder>  {

    Context context;
    List<Customer> mechanics;

    public CustomerViewAdapter(Context context, List<Customer> mechanics) {
        this.context = context;
        this.mechanics = mechanics;
    }


    @NonNull
    @NotNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_view, parent, false);
        CustomerViewHolder viewHolder = new CustomerViewHolder(view);//.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomerViewHolder viewHolder, int position) {


        final Customer customer = (Customer) this.getItem(position);

        viewHolder.tvName.setText("Customer Name : " + customer.getName());
        viewHolder.time.setText( customer.getTime());
        if(customer.getBudget()==0)
            viewHolder.price.setText("Urgent");
        else
            viewHolder.price.setText("Budget : " + customer.getBudget());
        if(customer.getDent_type()==null)
            viewHolder.Description.setText("Description : " + customer.getDescription());
        else
            viewHolder.Description.setText("Description : " + customer.getDescription()+"\nMake:"+customer.getMake()+"\nModel: "+customer.getModel()+" \nYear: "+customer.getYear()+"\nType: "+customer.getDent_type());
        if(customer.getService_type()!=null) {
            switch (customer.getService_type()) {
                case "fuel":
                    viewHolder.serviceType.setImageDrawable(context.getResources().getDrawable(R.drawable.fuel));
                    break;
                case "tires":
                    viewHolder.serviceType.setImageDrawable(context.getResources().getDrawable(R.drawable.tires));
                    break;
                case "towing":
                    viewHolder.serviceType.setImageDrawable(context.getResources().getDrawable(R.drawable.towing));
                    break;
                case "jump start":
                    viewHolder.serviceType.setImageDrawable(context.getResources().getDrawable(R.drawable.jumpstart));
                    break;
                default:
                    viewHolder.serviceType.setVisibility(View.GONE);
            }
        }else {
            viewHolder.serviceType.setVisibility(View.GONE);
        }
        viewHolder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("address", customer.getAddress());
                intent.putExtra("lat_long", customer.getLat()+":"+customer.getLongitude());
                context.startActivity(intent);
            }
        });
        viewHolder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://api.whatsapp.com/send?phone="+customer.getPhone();
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
                callIntent.setData(Uri.parse("tel:"+customer.getPhone()));//change the number
                context.startActivity(callIntent);
            }
        });
    }

    Customer getItem(int i){
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



}
