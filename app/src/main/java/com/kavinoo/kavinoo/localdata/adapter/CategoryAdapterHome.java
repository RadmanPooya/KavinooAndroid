package com.kavinoo.kavinoo.localdata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.SecondaryCategoryActivity;
import com.kavinoo.kavinoo.eventbus.MessageEvent;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.utils.AppUtils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CategoryAdapterHome extends RecyclerView.Adapter<CategoryAdapterHome.CategoryAdapterHomeViewHolder> {
    ArrayList<CategoriesItem> categoriesItemArrayList;
    Context context;

    public CategoryAdapterHome(ArrayList<CategoriesItem> categoryModelArrayList, Context context) {
        this.categoriesItemArrayList = categoryModelArrayList;
        CategoriesItem more=new CategoriesItem();
        more.setCategoryImage(R.drawable.more+"");
        more.setCategoryTitle("بیشتر");
        categoriesItemArrayList.add(more);
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapterHomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_home,parent,false);
        CategoryAdapterHomeViewHolder categoryViewHolder=new CategoryAdapterHomeViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapterHomeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if(position>7){

        }else{
            CategoriesItem more=new CategoriesItem();
            more.setCategoryImage(R.drawable.more+"");
            more.setCategoryTitle("بیشتر");
            if(position<=6){
                Picasso.with(context).load(categoriesItemArrayList.get(position).getCategoryImage()).fit().into(holder.categoryItemHomeImageView);
                holder.categoryItemHomeTextView.setText(categoriesItemArrayList.get(position).getCategoryTitle());
            }
            if (position==7){
                holder.categoryItemHomeImageView.setImageResource(R.drawable.more);
                holder.categoryItemHomeTextView.setText("بیشتر");
            }
            holder.categoryItemHomeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position<=6){
                        Intent intentToSecCat=new Intent(context, SecondaryCategoryActivity.class);
                        intentToSecCat.putExtra("category_id",categoriesItemArrayList.get(position).getCategoryId());
                        intentToSecCat.putExtra("category_image",categoriesItemArrayList.get(position).getCategoryImage());
                        intentToSecCat.putExtra("category_title",categoriesItemArrayList.get(position).getCategoryTitle());
                        intentToSecCat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intentToSecCat);
                    }
                    if (position==7){
                        EventBus.getDefault().post(new MessageEvent("MORECLICKED"));
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categoriesItemArrayList.size();
    }

    public static class CategoryAdapterHomeViewHolder extends RecyclerView.ViewHolder{
        ImageView categoryItemHomeImageView;
        TextView categoryItemHomeTextView;
        public CategoryAdapterHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItemHomeImageView=itemView.findViewById(R.id.category_item_home_image_view);
            categoryItemHomeTextView=itemView.findViewById(R.id.category_item_home_text_view);
        }
    }
}
