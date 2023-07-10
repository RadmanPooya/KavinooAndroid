package com.kavinoo.kavinoo.onlinedata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.model.facilitiesfilter.FacilitiesItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FilterFacilitiesAdapter extends RecyclerView.Adapter<FilterFacilitiesAdapter.FacilitiesViewHolder> {

    Context context;
    List<FacilitiesItem> facilitiesItemList;
    ISelectFacility iSelectFacility;

    public FilterFacilitiesAdapter(Context context, List<FacilitiesItem> facilitiesItemList, ISelectFacility iSelectFacility) {
        this.context = context;
        this.facilitiesItemList = facilitiesItemList;
        this.iSelectFacility = iSelectFacility;
    }

    @NonNull
    @Override
    public FacilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FilterFacilitiesAdapter.FacilitiesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.facilities_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final FacilitiesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.with(context).load(facilitiesItemList.get(position).getImage()).fit().into(holder.facilityImageView);
        holder.facilityNameTextView.setText(facilitiesItemList.get(position).getName());
        final boolean[] isSelected = {false};
        holder.filterItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSelected[0]){
                    holder.facilityNameTextView.setTextColor(Color.parseColor("#367ab9"));
                    holder.filterItemCardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
                    holder.filterCardImage.setCardBackgroundColor(Color.parseColor("#367ab9"));
                    iSelectFacility.onSelectFacility(facilitiesItemList.get(position).getId());
                    isSelected[0] = true;
                }else{
                    holder.facilityNameTextView.setTextColor(Color.parseColor("#C8C6C6"));
                    holder.filterItemCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"));
                    holder.filterCardImage.setCardBackgroundColor(Color.parseColor("#C8C6C6"));
                    iSelectFacility.onUnSelectFacility(facilitiesItemList.get(position).getId());
                    isSelected[0] = false;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return facilitiesItemList.size();
    }


    public interface ISelectFacility {
        void onSelectFacility(int selectedFacilityId);
        void onUnSelectFacility(int unSelectedFacilityId);
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
