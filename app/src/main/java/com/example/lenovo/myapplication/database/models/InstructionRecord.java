package com.example.lenovo.myapplication.database.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class InstructionRecord {
    @PrimaryKey(autoGenerate = true)
    public Long id;

    public String imageUrl;
    public String description;
    //public SLevel level;
}
