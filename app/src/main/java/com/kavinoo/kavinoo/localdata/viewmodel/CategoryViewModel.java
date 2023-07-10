package com.kavinoo.kavinoo.localdata.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.repository.CategoryRepository;
import com.kavinoo.kavinoo.localdata.task.CategoryInsertAsyncTask;
import com.kavinoo.kavinoo.localdata.task.CategoryMultiplyInsertAsyncTask;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);

    }
    public void insert(CategoriesItem categoriesItem){
        //categoryRepository.insert(categoryModel);
        new CategoryInsertAsyncTask(categoryRepository).execute(categoriesItem);
    }
    public void insert(List<CategoriesItem> categoriesItems){
        //categoryRepository.insert(categoryModel);
        new CategoryMultiplyInsertAsyncTask(categoryRepository).execute(categoriesItems);
    }
    public LiveData<List<CategoriesItem>> all(){
        return categoryRepository.all();
    }
    public LiveData<List<CategoriesItem>> mainCategories(){
        return categoryRepository.mainCategories();
    }
    public LiveData<List<CategoriesItem>> catWithParentId(String parentId){
        return categoryRepository.catWithParentId(parentId);
    }

    public LiveData<List<CategoriesItem>> search(String word){
        return categoryRepository.search(word);
    }

    public LiveData<List<CategoriesItem>> searchInSecondary(String parentId,String word) {
        return categoryRepository.searchInSecondary(parentId,word);
    }



    public LiveData<CategoriesItem> single(int id){
        return categoryRepository.single(id);
    }
    public void update(CategoriesItem categoriesItem){
        categoryRepository.update(categoriesItem);
    }
    public void delete(CategoriesItem categoriesItem){
        categoryRepository.delete(categoriesItem);
    }
}
