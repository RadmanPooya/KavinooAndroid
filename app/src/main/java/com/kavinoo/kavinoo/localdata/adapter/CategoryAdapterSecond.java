package com.kavinoo.kavinoo.localdata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.activity.PlacesWithCategoryActivity;
import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterSecond extends RecyclerView.Adapter<CategoryAdapterSecond.CategoryAdapterSecondViewHolder> {
    ArrayList<CategoriesItem> categoriesItemArrayList;
    Context context;
    OnClickSecondaryCategory onClickSecondaryCategory;

    public CategoryAdapterSecond(ArrayList<CategoriesItem> categoriesItemArrayList, Context context,OnClickSecondaryCategory onClickSecondaryCategory) {
        this.categoriesItemArrayList = categoriesItemArrayList;
        this.context = context;
        this.onClickSecondaryCategory=onClickSecondaryCategory;
    }

    @NonNull
    @Override
    public CategoryAdapterSecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_main, parent, false);
        CategoryAdapterSecondViewHolder categoryViewHolder = new CategoryAdapterSecondViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterSecondViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Picasso.with(context).load(categoriesItemArrayList.get(position).getCategoryImage()).fit().into(holder.categoryItemSecondImageView);
        holder.categoryItemSecondTextView.setText(categoriesItemArrayList.get(position).getCategoryTitle());


        holder.constraintSecondCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSecCat = new Intent(context, PlacesWithCategoryActivity.class);
                intentToSecCat.putExtra("category_id", categoriesItemArrayList.get(position).getCategoryId());
                intentToSecCat.putExtra("category_image", categoriesItemArrayList.get(position).getCategoryImage());
                intentToSecCat.putExtra("category_title", categoriesItemArrayList.get(position).getCategoryTitle());
                intentToSecCat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentToSecCat);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onClickSecondaryCategory.onClickSecondaryCategory();
                    }
                }, 1500);
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoriesItemArrayList.size();
    }

    public static class CategoryAdapterSecondViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintSecondCategory;
        ImageView categoryItemSecondImageView;
        TextView categoryItemSecondTextView;

        public CategoryAdapterSecondViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintSecondCategory = itemView.findViewById(R.id.constraint_main_category);
            categoryItemSecondImageView = itemView.findViewById(R.id.category_item_main_image_view);
            categoryItemSecondTextView = itemView.findViewById(R.id.category_item_main_text_view);
        }
    }

    public void updateList(List<CategoriesItem> list) {
        categoriesItemArrayList = (ArrayList<CategoriesItem>) list;

        notifyDataSetChanged();
    }

    public interface OnClickSecondaryCategory {
        void onClickSecondaryCategory();
    }
}
