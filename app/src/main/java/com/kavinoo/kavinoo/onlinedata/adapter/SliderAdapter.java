package com.kavinoo.kavinoo.onlinedata.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.model.slider.SliderItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private List<SliderItem> sliderItemArrayList;
    private ViewPager2 viewPagerSlider;
    private Context context;

    public SliderAdapter(List<SliderItem> sliderItemArrayList, ViewPager2 viewPagerSlider, Context context) {
        this.sliderItemArrayList = sliderItemArrayList;
        this.viewPagerSlider = viewPagerSlider;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderAdapter.SliderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        Picasso.with(context).load(sliderItemArrayList.get(position).getImageAddress()).fit().into(holder.sliderImageView);
        if(position==sliderItemArrayList.size()-2){
            viewPagerSlider.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItemArrayList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView sliderImageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);

            sliderImageView=itemView.findViewById(R.id.slider_image_view);

        }

    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sliderItemArrayList.addAll(sliderItemArrayList);
            notifyDataSetChanged();
        }
    };
}
