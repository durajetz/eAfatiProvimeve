package com.example.eafatiprovimeve.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eafatiprovimeve.ViewModel.AfatiProvimeve;

import java.util.List;

public class AfatiProvimeveModel extends AndroidViewModel {
    private AfatiProvimeveRepository repository;
    private LiveData<List<AfatiProvimeve>> allProvimet;


    public AfatiProvimeveModel(@NonNull Application application) {
        super(application);
        repository = new AfatiProvimeveRepository(application);
        allProvimet = repository.getAllProvimet();
    }

    public void insert(AfatiProvimeve afatiProvimeve){
        repository.insert(afatiProvimeve);
    }

    public void update(AfatiProvimeve afatiProvimeve){
        repository.update(afatiProvimeve);
    }

    public void delete(AfatiProvimeve afatiProvimeve){
        repository.delete(afatiProvimeve);
    }

    public void deleteAllProvimet(){
        repository.deleteAllAfatiProvimeve();
    }

    public LiveData<List<AfatiProvimeve>> getAllProvimet() {
        return allProvimet;
    }
}
