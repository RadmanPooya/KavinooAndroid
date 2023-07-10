package com.kavinoo.kavinoo.localdata.task;

import android.os.AsyncTask;

import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

public class CategoryMultiplyInsertAsyncTask extends AsyncTask<List<CategoriesItem>,Void,Void> {

    private CategoryRepository categoryRepository;

    public CategoryMultiplyInsertAsyncTask(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    protected Void doInBackground(List<CategoriesItem>... categoriesItem) {
        categoryRepository.insert(categoriesItem[0]);
        return null;
    }

}
