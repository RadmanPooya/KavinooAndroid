package com.kavinoo.kavinoo.localdata.task;

import android.os.AsyncTask;

import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.repository.CategoryRepository;

public class CategoryInsertAsyncTask extends AsyncTask<CategoriesItem,Void,Void> {

    private CategoryRepository categoryRepository;

    public CategoryInsertAsyncTask(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Void doInBackground(CategoriesItem... categoriesItems) {
        categoryRepository.insert(categoriesItems[0]);
        return null;
    }
}
