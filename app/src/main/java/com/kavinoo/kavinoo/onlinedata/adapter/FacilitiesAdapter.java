package com.kavinoo.kavinoo.onlinedata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;

import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.FacilitiesItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilitiesViewHolder> {

    Context context;
    List<FacilitiesItem> facilitiesItemList;

    public FacilitiesAdapter(Context context, List<FacilitiesItem> facilitiesItemList) {
        this.context = context;
        this.facilitiesItemList = facilitiesItemList;
    }

    @NonNull
    @Override
    public FacilitiesAdapter.FacilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FacilitiesAdapter.FacilitiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.facilities_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final FacilitiesAdapter.FacilitiesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        try {
            if(facilitiesItemList.get(position).getFacility()==null){
                Log.i("QAQAZ","is nullll");
                holder.facilityNameTextView.setText(facilitiesItemList.get(position).getName());
            }else{
                Log.i("QAQAZ","is notttttttttttt   nullll");
                if(facilitiesItemList.get(position).getFacility().getImage().equals("")){

                }else{
                    Picasso.with(context).load(facilitiesItemList.get(position).getFacility().getImage()).fit().into(holder.facilityImageView);
                }
                holder.facilityNameTextView.setText(facilitiesItemList.get(position).getFacility().getName());
            }

            Log.i("QAQAZ",facilitiesItemList.toString());

            holder.facilityNameTextView.setTextColor(Color.parseColor("#6d5829"));
            holder.filterItemCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.filterCardImage.setCardBackgroundColor(Color.parseColor("#6d5829"));
        }catch (Exception e){

        }


    }

    @Override
    public int getItemCount() {
        return facilitiesItemList.size();
    }


    public static class FacilitiesViewHolder extends RecyclerView.ViewHolder {

        CardView filterItemCardView;
        CardView filterCardImage;
        ImageView facilityImageView;
        TextView facilityNameTextView;

        public FacilitiesViewHolder(@NonNull View itemView) {
            super(itemView);

            filterItemCardView = itemView.findViewById(R.id.filter_item_card_view);
            filterCardImage = itemView.findViewById(R.id.filter_card_image);
            facilityImageView = itemView.findViewById(R.id.facility_image_view);
            facilityNameTextView = itemView.findViewById(R.id.facility_name_text_view);
        }
    }


}
