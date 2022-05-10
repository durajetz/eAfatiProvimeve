package com.example.eafatiprovimeve.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eafatiprovimeve.ViewModel.AfatiProvimeve;

import java.util.List;
import java.util.stream.Collectors;

public class AfatiProvimeveModel extends AndroidViewModel {
    private AfatiProvimeveRepository repository;
    private LiveData<List<AfatiProvimeve>> allProvimet;
    private LiveData<List<AfatiProvimeve>> allProvimetSortedByName;


    public AfatiProvimeveModel(@NonNull Application application) {
        super(application);
        repository = new AfatiProvimeveRepository(application);
        allProvimet = repository.getAllProvimet();
        allProvimetSortedByName = repository.getAllProvimetSortedByName();
    }

    public void insert(AfatiProvimeve afatiProvimeve) {
        repository.insert(afatiProvimeve);
    }

    public void update(AfatiProvimeve afatiProvimeve) {
        repository.update(afatiProvimeve);
    }

    public void delete(AfatiProvimeve afatiProvimeve) {
        repository.delete(afatiProvimeve);
    }

    public void deleteAllProvimet() {
        repository.deleteAllAfatiProvimeve();
    }

    public LiveData<List<AfatiProvimeve>> getAllProvimet() {
        return allProvimet;
    }
    public LiveData<List<AfatiProvimeve>> getAllProvimetSortedByName() { return allProvimetSortedByName; }

    public List<AfatiProvimeve> getProvimetByYear(String year) {
        return allProvimet.getValue().stream().filter(l -> l.getViti().equals(year)).collect(Collectors.toList());
    }

    public List<AfatiProvimeve> getProvimetBySemster(String semester) {
        return allProvimet.getValue().stream().filter(l -> l.getSemestri().equals(semester)).collect(Collectors.toList());
    }

    public List<AfatiProvimeve> getSorted()
    {
        return allProvimetSortedByName.getValue();
    }
}
