package com.kavinoo.kavinoo.onlinedata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.PlaceImageSliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlaceImageSliderAdapter extends SliderViewAdapter<PlaceImageSliderAdapter.SliderAdapterVH> {
    private Context context;
    private List<PlaceImageSliderModel> mPlaceImageSliderModels = new ArrayList<>();

    public PlaceImageSliderAdapter(Context context) {
        this.context = context;
    }

    public void renewItems(List<PlaceImageSliderModel> placeImageSliderModels) {
        this.mPlaceImageSliderModels = placeImageSliderModels;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mPlaceImageSliderModels.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(PlaceImageSliderModel placeImageSliderModel) {
        this.mPlaceImageSliderModels.add(placeImageSliderModel);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        PlaceImageSliderModel placeImageSliderModel = mPlaceImageSliderModels.get(position);


        Glide.with(viewHolder.itemView)
                .load(placeImageSliderModel.getImage())
                .centerCrop()
                .into(viewHolder.palceImageItem);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mPlaceImageSliderModels.size();

    }

    static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView palceImageItem;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            palceImageItem = itemView.findViewById(R.id.palce_image_item);
        }
    }
}
