package com.example.lenovo.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.lenovo.myapplication.database.models.LevelRecord;

@Database(entities = {LevelRecord.class}, version = 1)
public abstract class LevelDatabase extends RoomDatabase{
    public abstract LevelDao levelDao();
}


