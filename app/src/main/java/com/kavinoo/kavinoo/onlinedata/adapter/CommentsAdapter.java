package com.kavinoo.kavinoo.onlinedata.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.PlaceActivity;
import com.kavinoo.kavinoo.onlinedata.model.place.placedetail.CommentsItem;

import org.aviran.cookiebar2.CookieBar;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    Context context;
    List<CommentsItem> commentsItemList;
    Activity myActivity;

    public CommentsAdapter(Context context, List<CommentsItem> commentsItemList,Activity myActivity) {
        this.context = context;
        this.commentsItemList = commentsItemList;
        this.myActivity = myActivity;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentsAdapter.CommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        holder.user.setText(commentsItemList.get(position).getUser());
        holder.body.setText(commentsItemList.get(position).getBody());
        holder.reportIconComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReportComment();
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsItemList.size();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView user;
        TextView body;
        CardView reportIconComment;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            user=itemView.findViewById(R.id.user);
            body=itemView.findViewById(R.id.body);
            reportIconComment=itemView.findViewById(R.id.report_icon_comment);
        }
    }

    public void showReportComment() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.report_comment_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        LinearLayout reportPlaceLinear;

        reportPlaceLinear = dialog.findViewById(R.id.report_place_linear);

        reportPlaceLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                CookieBar.Builder c = CookieBar.build(myActivity);
                c.setTitle("گزارش شما با موفقیت ثبت شد");
                c.setSwipeToDismiss(true);
                c.setDuration(3500);
                c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
            }
        });

        dialog.show();
    }


}
