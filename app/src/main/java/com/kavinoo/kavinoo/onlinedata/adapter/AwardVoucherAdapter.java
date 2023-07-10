package com.kavinoo.kavinoo.onlinedata.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.kavinoo.App;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.PlaceActivity;
import com.kavinoo.kavinoo.onlinedata.links.KavinooLinks;
import com.kavinoo.kavinoo.onlinedata.model.awardvoucher.DataItem;
import com.kavinoo.kavinoo.utils.TokenContainer;
import com.mackhartley.roundedprogressbar.ProgressTextFormatter;
import com.mackhartley.roundedprogressbar.RoundedProgressBar;
import com.squareup.picasso.Picasso;

import org.aviran.cookiebar2.CookieBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AwardVoucherAdapter extends RecyclerView.Adapter<AwardVoucherAdapter.AwardVoucherViewHolder> {

    Context context;
    List<DataItem> awardVoucherList;
    String userScore;
    Activity myActivity;
    IAwardSuccess iAwardSuccess;

    public AwardVoucherAdapter(Context context, List<DataItem> awardVoucherList, String userScore,Activity myActivity,IAwardSuccess iAwardSuccess) {
        this.context = context;
        this.awardVoucherList = awardVoucherList;
        this.userScore = userScore;
        this.myActivity = myActivity;
        this.iAwardSuccess = iAwardSuccess;
    }

    @NonNull
    @Override
    public AwardVoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AwardVoucherAdapter.AwardVoucherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.award_voucher_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull AwardVoucherViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.scoreTextView.setText(String.valueOf(awardVoucherList.get(position).getScore()));
        try {
            Picasso.with(context).load(awardVoucherList.get(position).getImage()).fit().into(holder.awardVoucherImage);

            holder.awardVoucherTitle.setText(String.valueOf(awardVoucherList.get(position).getTitle()));
            int p1 = Integer.parseInt(userScore);
            int total = awardVoucherList.get(position).getScore();
            if (p1 >= total) {
                holder.progressAwardVoucher.setVisibility(View.INVISIBLE);
                holder.awardVoucherCard.setVisibility(View.VISIBLE);
            } else {
                holder.awardVoucherCard.setVisibility(View.INVISIBLE);
                holder.progressAwardVoucher.setVisibility(View.VISIBLE);
                float pF = p1;
                float totalF = total;
                double percent = pF / totalF;
                percent = percent * 100;
                percent = percent / 1;
                holder.progressAwardVoucher.setProgressPercentage(percent, false);
            }
            holder.awardVoucherCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    awardVoucherTransAction(String.valueOf(awardVoucherList.get(position).getId()));
                }
            });

        } catch (Exception e) {


        }


    }

    @Override
    public int getItemCount() {
        return awardVoucherList.size();
    }

    public static class AwardVoucherViewHolder extends RecyclerView.ViewHolder {

        TextView scoreTextView;
        ImageView awardVoucherImage;
        TextView awardVoucherTitle;
        RoundedProgressBar progressAwardVoucher;
        CardView awardVoucherCard;


        public AwardVoucherViewHolder(@NonNull View itemView) {
            super(itemView);

            scoreTextView = itemView.findViewById(R.id.score_text_view);
            awardVoucherImage = itemView.findViewById(R.id.award_voucher_image);
            awardVoucherTitle = itemView.findViewById(R.id.award_voucher_title);
            progressAwardVoucher = itemView.findViewById(R.id.progress_award_voucher);
            awardVoucherCard = itemView.findViewById(R.id.award_voucher_card);


        }
    }


    public void awardVoucherTransAction(final String awardVoucherTransaction){

        final Dialog dialog = new Dialog(myActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_award_voucher_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView addAwardVoucherCadr;
        final TextView addAwardVoucherTextView;
        final ProgressBar spinKitAddAwardVoucher;

        addAwardVoucherCadr = dialog.findViewById(R.id.add_award_voucher_cadr);
        addAwardVoucherTextView = dialog.findViewById(R.id.add_award_voucher_text_view);
        spinKitAddAwardVoucher = dialog.findViewById(R.id.spin_kit_add_award_voucher);

        ThreeBounce threeBounce = new ThreeBounce();
        spinKitAddAwardVoucher.setIndeterminateDrawable(threeBounce);
        spinKitAddAwardVoucher.setVisibility(View.INVISIBLE);

        addAwardVoucherCadr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAwardVoucherTextView.setVisibility(View.INVISIBLE);
                spinKitAddAwardVoucher.setVisibility(View.VISIBLE);

                StringRequest awardvoucherTransactionReq = new StringRequest(Request.Method.GET, KavinooLinks.ADD_AWARD_VOUCHER_TRANSACTION+"/"+awardVoucherTransaction, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        iAwardSuccess.onSuccessAward(awardVoucherTransaction);
                        addAwardVoucherTextView.setVisibility(View.VISIBLE);
                        spinKitAddAwardVoucher.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                        CookieBar.Builder c = CookieBar.build(myActivity);
                        c.setTitle("با موفقیت ثبت شد");
                        c.setSwipeToDismiss(true);
                        c.setDuration(3500);
                        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        addAwardVoucherTextView.setVisibility(View.VISIBLE);
                        spinKitAddAwardVoucher.setVisibility(View.INVISIBLE);
                        dialog.dismiss();
                        CookieBar.Builder c = CookieBar.build(myActivity);
                        c.setTitle("خطا در اتصال به سرور");
                        c.setSwipeToDismiss(true);
                        c.setDuration(3500);
                        c.setCookiePosition(CookieBar.BOTTOM); // Cookie will be displayed at the bottom
                        ViewCompat.setLayoutDirection(c.show().getView(), ViewCompat.LAYOUT_DIRECTION_RTL);
                    }
                }
                ) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new HashMap<String, String>();
                        header.put("Authorization", "Bearer " + TokenContainer.getToken());
                        return header;
                    }

                };

                awardvoucherTransactionReq.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                App.getInstance().addToRequestQueue(awardvoucherTransactionReq, "ADDCOMMENTREQ");
            }
        });
        dialog.show();



    }

    public interface IAwardSuccess {
        void onSuccessAward(String awardId);
    }


}
