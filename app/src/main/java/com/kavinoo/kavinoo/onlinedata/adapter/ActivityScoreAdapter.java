package com.kavinoo.kavinoo.onlinedata.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.onlinedata.model.activityscore.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityScoreAdapter extends RecyclerView.Adapter<ActivityScoreAdapter.ActivityScoreViewHolder> {

    Context context;
    List<DataItem> activityScoreList;

    public ActivityScoreAdapter(Context context, List<DataItem> activityScoreList) {
        this.context = context;
        this.activityScoreList = activityScoreList;
    }

    @NonNull
    @Override
    public ActivityScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityScoreAdapter.ActivityScoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityScoreViewHolder holder, int position) {

        holder.scoreTextView.setText(String.valueOf(activityScoreList.get(position).getScore()));
        try {
            Picasso.with(context).load(activityScoreList.get(position).getImage()).fit().into(holder.scoreImage);
        }catch (Exception e){

        }
        holder.scoreTitle.setText(String.valueOf(activityScoreList.get(position).getTitle()));
    }

    @Override
    public int getItemCount() {
        return activityScoreList.size();
    }

    public static class ActivityScoreViewHolder extends RecyclerView.ViewHolder {

        TextView scoreTextView;
        ImageView scoreImage;
        TextView scoreTitle;

        public ActivityScoreViewHolder(@NonNull View itemView) {
            super(itemView);

            scoreTextView=itemView.findViewById(R.id.score_text_view);
            scoreImage=itemView.findViewById(R.id.score_image);
            scoreTitle=itemView.findViewById(R.id.score_title);

        }
    }


}
