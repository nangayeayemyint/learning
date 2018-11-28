package com.example.lenovo.myapplication.database.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "levelRecord")
public class LevelRecord {

    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String levelId;

    public long order;

    public String name;

    public String description;
}
