package com.kavinoo.kavinoo.onlinedata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.DigitalMenusItem;

import java.util.List;

public class DigitalMenuAdapter extends RecyclerView.Adapter<DigitalMenuAdapter.DigitalMenuViewHolder> {

    Context context;
    List<DigitalMenusItem> digitalMenusItems;

    public DigitalMenuAdapter(Context context, List<DigitalMenusItem> digitalMenusItems) {
        this.context = context;
        this.digitalMenusItems = digitalMenusItems;
        Log.i("DDDGGG",digitalMenusItems.toString());
    }

    @NonNull
    @Override
    public DigitalMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DigitalMenuAdapter.DigitalMenuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.digital_menu_item, parent, false));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBindViewHolder(@NonNull DigitalMenuViewHolder holder, int position) {
        Glide.with(context).load(digitalMenusItems.get(position).getImage()).centerCrop().into(holder.image);
        holder.title.setText(digitalMenusItems.get(position).getTitle());
        holder.description.setText(digitalMenusItems.get(position).getDescription());
        holder.price.setText(digitalMenusItems.get(position).getPrice()+" تومان ");

    }

    @Override
    public int getItemCount() {
        return digitalMenusItems.size();
    }

    public static class DigitalMenuViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView description;
        TextView price;

        public DigitalMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            price=itemView.findViewById(R.id.price);
        }
    }
}
