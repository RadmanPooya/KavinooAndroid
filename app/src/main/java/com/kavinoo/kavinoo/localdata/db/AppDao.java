package com.kavinoo.kavinoo.localdata.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kavinoo.kavinoo.localdata.model.category.CategoriesItem;
import com.kavinoo.kavinoo.localdata.model.placedetail.PlaceDetails;

import java.util.List;

@Dao
public interface AppDao {
    //crud

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(CategoriesItem categoriesItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(PlaceDetails placeDetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<CategoriesItem> categoriesItems);

    @Query("select * from categoryTable where parentId=:parentId and categoryTitle like :word")
    public LiveData<List<CategoriesItem>> searchInSecondary(String parentId,String word);

    @Query("select * from categoryTable where categoryTitle like :word")
    public LiveData<List<CategoriesItem>> search(String word);

    @Query("select * from categoryTable")
    public LiveData<List<CategoriesItem>> all();

    @Query("select * from favoritePlaceTable")
    public LiveData<List<PlaceDetails>> allFavorite();

    @Query("select * from categoryTable where parentId=0")
    public LiveData<List<CategoriesItem>> mainCategories();

    @Query("select * from categoryTable where parentId=:parentId")
    public LiveData<List<CategoriesItem>> catWithParentId(String parentId);

    @Query("select * from categoryTable where categoryId=:id")
    public LiveData<CategoriesItem> single(int id);

    @Update()
    public void update(CategoriesItem categoriesItem);

    @Delete
    public void delete(CategoriesItem categoriesItem);

}
