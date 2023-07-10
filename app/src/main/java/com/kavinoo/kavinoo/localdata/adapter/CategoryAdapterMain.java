package com.kavinoo.kavinoo.localdata.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.kavinoo.kavinoo.R;
import com.kavinoo.kavinoo.activity.MainActivity;
import com.kavinoo.kavinoo.activity.SecondaryCategoryActivity;
import com.kavinoo.kavinoo.activity.SplashScreen;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.utils.AppUtils;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterMain extends RecyclerView.Adapter<CategoryAdapterMain.CategoryAdapterMainViewHolder> {
    ArrayList<CategoriesItem> categoriesItemArrayList;
    Context context;
    OnClickCategoryMain onClickCategoryMain;

    public CategoryAdapterMain(ArrayList<CategoriesItem> categoriesItemArrayList, Context context,OnClickCategoryMain onClickCategoryMain) {
        this.categoriesItemArrayList = categoriesItemArrayList;
        this.context = context;
        this.onClickCategoryMain=onClickCategoryMain;
    }

    @NonNull
    @Override
    public CategoryAdapterMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_main,parent,false);
        CategoryAdapterMainViewHolder categoryViewHolder=new CategoryAdapterMainViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterMainViewHolder holder, @SuppressLint("RecyclerView") final int position) {

            Picasso.with(context).load(categoriesItemArrayList.get(position).getCategoryImage()).fit().into(holder.categoryItemMainImageView);
            holder.categoryItemMainTextView.setText(categoriesItemArrayList.get(position).getCategoryTitle());
            AppUtils.logi(categoriesItemArrayList.get(position).getCategoryImage());
            holder.constraintMainCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intentToSecCat=new Intent(context, SecondaryCategoryActivity.class);
                    intentToSecCat.putExtra("category_id",categoriesItemArrayList.get(position).getCategoryId());
                    intentToSecCat.putExtra("category_image",categoriesItemArrayList.get(position).getCategoryImage());
                    intentToSecCat.putExtra("category_title",categoriesItemArrayList.get(position).getCategoryTitle());
                    intentToSecCat.putExtra("parent_id",categoriesItemArrayList.get(position).getParentId());
                    intentToSecCat.putExtra("description",categoriesItemArrayList.get(position).getDescription());
                    intentToSecCat.putExtra("has_child",categoriesItemArrayList.get(position).isHasChild());
                    intentToSecCat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentToSecCat);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onClickCategoryMain.onClickCategory();
                        }
                    }, 1500);

                }
            });

    }

    @Override
    public int getItemCount() {
        return categoriesItemArrayList.size();
    }

    public static class CategoryAdapterMainViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintMainCategory;
        ImageView categoryItemMainImageView;
        TextView categoryItemMainTextView;
        public CategoryAdapterMainViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintMainCategory=itemView.findViewById(R.id.constraint_main_category);
            categoryItemMainImageView=itemView.findViewById(R.id.category_item_main_image_view);
            categoryItemMainTextView=itemView.findViewById(R.id.category_item_main_text_view);
        }
    }

    public void updateList(List<CategoriesItem> list){
        categoriesItemArrayList = (ArrayList<CategoriesItem>) list;
        notifyDataSetChanged();
    }

    public interface OnClickCategoryMain {
        void onClickCategory();
    }
}
