package com.kavinoo.kavinoo.onlinedata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.kavinoo.kavinoo.activity.PlaceActivity;
import com.kavinoo.kavinoo.onlinedata.model.place.placelist.PlacesItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlacesListAdapterVip extends RecyclerView.Adapter<PlacesListAdapterVip.PlaceListViewHolder> {

    List<PlacesItem> placesItemList;
    Context context;

    public PlacesListAdapterVip(List<PlacesItem> placesItemList, Context context) {
        this.placesItemList = placesItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public PlaceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlacesListAdapterVip.PlaceListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.places_item_vip,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceListViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Picasso.with(context).load(placesItemList.get(position).getHeaderImage()).fit().into(holder.headerImagePlaceListImageView);
        holder.titlePlaceListTextView.setText(placesItemList.get(position).getTitle());
        double distance=placesItemList.get(position).getDistance();

        String distanceText="";
        if(distance>=1000){
            double dm=distance/1000;
            int d=(int)dm;
            distanceText=d+" کیلومتری شما";
        }else {
            int d=(int)distance;
            distanceText=d+" متری شما";
        }

        holder.distancePlaceListTextView.setText(distanceText);

        Log.i("FACILI","adapter list : "+placesItemList.toString());

        holder.addressPlaceListTextView.setText(placesItemList.get(position).getAddress());
        holder.visitCountPlaceListTextView.setText(String.valueOf(placesItemList.get(position).getVisitCount()));
        holder.commentCountPlaceListTextView.setText(String.valueOf(placesItemList.get(position).getCommentsCount()));
        holder.favoriteCountPlaceListTextView.setText(String.valueOf(placesItemList.get(position).getFavoriteCount()));

        int rondRate=(int)placesItemList.get(position).getRate();
        if(rondRate==0){
            holder.rateImageView.setImageResource(R.drawable.rate0);
        }
        if(rondRate==1){
            holder.rateImageView.setImageResource(R.drawable.rate1);
        }
        if(rondRate==2){
            holder.rateImageView.setImageResource(R.drawable.rate2);
        }
        if(rondRate==3){
            holder.rateImageView.setImageResource(R.drawable.rate3);
        }
        if(rondRate==4){
            holder.rateImageView.setImageResource(R.drawable.rate4);
        }
        if(rondRate==5){
            holder.rateImageView.setImageResource(R.drawable.rate5);
        }

        final String finalDistanceText = distanceText;
        holder.placeItemBorderCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, PlaceActivity.class);
                intent.putExtra("idPlace",placesItemList.get(position).getId());
                intent.putExtra("distance", finalDistanceText);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return placesItemList.size();
    }

    public static class PlaceListViewHolder extends RecyclerView.ViewHolder{

        ImageView headerImagePlaceListImageView;
        TextView titlePlaceListTextView;
        TextView distancePlaceListTextView;
        TextView addressPlaceListTextView;
        TextView visitCountPlaceListTextView;
        TextView commentCountPlaceListTextView;
        TextView favoriteCountPlaceListTextView;
        CardView placeItemBorderCardView;
        ImageView rateImageView;


        public PlaceListViewHolder(@NonNull View itemView) {
            super(itemView);
            headerImagePlaceListImageView=itemView.findViewById(R.id.header_image_place_list);
            titlePlaceListTextView=itemView.findViewById(R.id.title_place_list);
            distancePlaceListTextView=itemView.findViewById(R.id.distance_place_list);
            addressPlaceListTextView=itemView.findViewById(R.id.address_place_list);
            visitCountPlaceListTextView=itemView.findViewById(R.id.visit_count_place_list);
            commentCountPlaceListTextView=itemView.findViewById(R.id.comment_count_place_list);
            favoriteCountPlaceListTextView=itemView.findViewById(R.id.favorite_count_place_list);
            placeItemBorderCardView=itemView.findViewById(R.id.place_item_border_card_view);
            rateImageView=itemView.findViewById(R.id.rate_image_view);

        }
    }
}
