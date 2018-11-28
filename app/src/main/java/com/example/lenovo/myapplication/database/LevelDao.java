package com.example.lenovo.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.graphics.ColorSpace;

import com.example.lenovo.myapplication.database.models.LevelRecord;

import java.util.List;

@Dao
public interface LevelDao {
    @Query("SELECT * FROM levelRecord")
    List<LevelRecord> getAll();

    @Query("SELECT * FROM levelRecord WHERE id IN (:levelID)")
    List<LevelRecord> loadAllByIds(String levelID);


    @Insert
    void insertAll(LevelRecord... levelRecord);

    @Insert
    void insert(LevelRecord levelRecord);

    @Delete
    void delete(LevelRecord levelRecord);
}
