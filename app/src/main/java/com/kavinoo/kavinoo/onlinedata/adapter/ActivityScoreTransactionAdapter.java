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
import com.kavinoo.kavinoo.onlinedata.model.activityscoretransaction.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityScoreTransactionAdapter extends RecyclerView.Adapter<ActivityScoreTransactionAdapter.ActivityScoreTransactionViewHolder> {

    Context context;
    List<DataItem> activityScoreList;

    public ActivityScoreTransactionAdapter(Context context, List<DataItem> activityScoreList) {
        this.context = context;
        this.activityScoreList = activityScoreList;
    }

    @NonNull
    @Override
    public ActivityScoreTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityScoreTransactionAdapter.ActivityScoreTransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_score_transaction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityScoreTransactionViewHolder holder, int position) {

        holder.activityScoreValue.setText(String.valueOf(activityScoreList.get(position).getActivityScore().getScore()+" امتیاز "));
        try {
            Picasso.with(context).load(activityScoreList.get(position).getActivityScore().getImage()).fit().into(holder.activityScoreImage);

            holder.activityScoreTitle.setText(String.valueOf(activityScoreList.get(position).getActivityScore().getTitle()));

            String[] splited = activityScoreList.get(position).getCreatedAt().split("\\s+");
            holder.dateTimeActivityScore.setText(splited[1]+" , "+splited[0]);

        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return activityScoreList.size();
    }

    public static class ActivityScoreTransactionViewHolder extends RecyclerView.ViewHolder {

        ImageView activityScoreImage;
        TextView activityScoreTitle;
        TextView activityScoreValue;
        TextView dateTimeActivityScore;

        public ActivityScoreTransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            activityScoreImage=itemView.findViewById(R.id.activity_score_image);
            activityScoreTitle=itemView.findViewById(R.id.activity_score_title);
            activityScoreValue=itemView.findViewById(R.id.activity_score_value);
            dateTimeActivityScore=itemView.findViewById(R.id.date_time_activity_score);

        }
    }


}
