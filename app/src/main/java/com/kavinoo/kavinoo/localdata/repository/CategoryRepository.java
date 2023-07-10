package com.kavinoo.kavinoo.localdata.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.localdata.db.AppDao;
import com.kavinoo.kavinoo.localdata.db.AppDatabase;
import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;

import java.util.List;

public class CategoryRepository {
    private AppDao appDao;


    public CategoryRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        appDao = appDatabase.appDao();
    }

    public void insert(CategoriesItem categoriesItem) {
        appDao.insert(categoriesItem);
    }

    public void insert(PlaceDetails placeDetails) {
        appDao.insert(placeDetails);
    }

    public void insert(List<CategoriesItem> categoriesItems) {
        appDao.insert(categoriesItems);
    }

    public LiveData<List<CategoriesItem>> all() {
        return appDao.all();
    }
    public LiveData<List<CategoriesItem>> mainCategories() {
        return appDao.mainCategories();
    }

    public LiveData<List<CategoriesItem>> catWithParentId(String parentId) {
        return appDao.catWithParentId(parentId);
    }

    public LiveData<List<CategoriesItem>> search(String word) {
        return appDao.search(word);
    }

    public LiveData<List<CategoriesItem>> searchInSecondary(String parentId,String word) {
        return appDao.searchInSecondary(parentId,word);
    }

    public LiveData<CategoriesItem> single(int id) {
        return appDao.single(id);
    }

    public void update(CategoriesItem categoriesItem) {
        appDao.update(categoriesItem);
    }

    public void delete(CategoriesItem categoriesItem) {
        appDao.delete(categoriesItem);
    }

}
