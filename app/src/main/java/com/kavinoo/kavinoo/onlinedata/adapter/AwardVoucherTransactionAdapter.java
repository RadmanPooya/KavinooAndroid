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
import com.kavinoo.kavinoo.onlinedata.model.awardvouchertransaction.DataItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AwardVoucherTransactionAdapter extends RecyclerView.Adapter<AwardVoucherTransactionAdapter.AwardVoucherTransactionViewHolder> {

    Context context;
    List<DataItem> awardVoucherList;

    public AwardVoucherTransactionAdapter(Context context, List<DataItem> awardVoucherList) {
        this.context = context;
        this.awardVoucherList = awardVoucherList;
    }

    @NonNull
    @Override
    public AwardVoucherTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AwardVoucherTransactionAdapter.AwardVoucherTransactionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.award_voucher_transaction_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AwardVoucherTransactionViewHolder holder, int position) {

        holder.awardVoucherValue.setText(String.valueOf(awardVoucherList.get(position).getAwardVoucher().getScore()+" امتیاز "));
        try {
            Picasso.with(context).load(awardVoucherList.get(position).getAwardVoucher().getImage()).fit().into(holder.awardVoucherImage);

            holder.awardVoucherTitle.setText(String.valueOf(awardVoucherList.get(position).getAwardVoucher().getTitle()));
            String[] splited = awardVoucherList.get(position).getCreatedAt().split("\\s+");
            holder.dateTimeAwardVoucher.setText(splited[1]+" , "+splited[0]);
            holder.awardDataTextView.setText(awardVoucherList.get(position).getAwardVoucher().getData());

        }catch (Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return awardVoucherList.size();
    }

    public static class AwardVoucherTransactionViewHolder extends RecyclerView.ViewHolder {

        ImageView awardVoucherImage;
        TextView awardVoucherTitle;
        TextView awardVoucherValue;
        TextView dateTimeAwardVoucher;
        TextView awardDataTextView;

        public AwardVoucherTransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            awardVoucherImage=itemView.findViewById(R.id.award_voucher_image);
            awardVoucherTitle=itemView.findViewById(R.id.award_voucher_title);
            awardVoucherValue=itemView.findViewById(R.id.award_voucher_value);
            dateTimeAwardVoucher=itemView.findViewById(R.id.date_time_award_voucher);
            awardDataTextView=itemView.findViewById(R.id.award_data_text_view);

        }
    }


}
