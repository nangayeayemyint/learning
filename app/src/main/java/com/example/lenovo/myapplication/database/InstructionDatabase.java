package com.example.lenovo.myapplication.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.lenovo.myapplication.database.models.InstructionRecord;

@Database(entities = {InstructionRecord.class}, version = 1)
public abstract class InstructionDatabase extends RoomDatabase{
    public abstract InstructionDao instructionDao();
}
