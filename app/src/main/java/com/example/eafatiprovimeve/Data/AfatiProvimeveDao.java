package com.example.eafatiprovimeve.Data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;


import com.example.eafatiprovimeve.ViewModel.AfatiProvimeve;

import java.util.List;

@Dao
public interface AfatiProvimeveDao {

    @Insert
    void insert(AfatiProvimeve afatiProvimeve);

    @Update
    void update(AfatiProvimeve afatiProvimeve);

    @Delete
    void delete(AfatiProvimeve afatiProvimeve);

    @Query("DELETE FROM afati_provimeve")
    void deleteAllAfatiProvimeve();

    @Query("SELECT * FROM afati_provimeve order by diferenca asc")
    LiveData<List<AfatiProvimeve>> getAllProvimet();

    @Query("SELECT * FROM afati_provimeve order by name asc")
    LiveData<List<AfatiProvimeve>> getAllProvimetSortedByName();
}
