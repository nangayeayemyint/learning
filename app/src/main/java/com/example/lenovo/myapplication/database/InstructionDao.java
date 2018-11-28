package com.example.lenovo.myapplication.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lenovo.myapplication.database.models.InstructionRecord;

import java.util.List;

@Dao
public interface InstructionDao {
    @Query("SELECT * FROM InstructionRecord")
    List<InstructionRecord> getAll();

    @Insert
    void insertAll(InstructionRecord... instructionRecords);

    @Insert
    void insert(InstructionRecord instructionRecord);

    @Delete
    void delete(InstructionRecord instructionRecord);
}
