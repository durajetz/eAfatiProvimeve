package com.example.eafatiprovimeve;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AfatiProvimeveRepository {
    private AfatiProvimeveDao afatiProvimeveDao;
    private LiveData<List<AfatiProvimeve>> allProvimet;

    public AfatiProvimeveRepository(Application application){
        AfatiProvimeveDb database = AfatiProvimeveDb.getInstance(application);
        afatiProvimeveDao = database.afatiProvimeveDao();
        allProvimet = afatiProvimeveDao.getAllProvimet();
    }

    public void insert(AfatiProvimeve afatiProvimeve){
        new InsertProvimiAsyncTask(afatiProvimeveDao).execute(afatiProvimeve);
    }

    public void update(AfatiProvimeve afatiProvimeve){
        new UpdateProvimiAsyncTask(afatiProvimeveDao).execute(afatiProvimeve);
    }

    public void delete(AfatiProvimeve afatiProvimeve){
        new DeleteProvimiAsyncTask(afatiProvimeveDao).execute(afatiProvimeve);
    }

    public void deleteAllAfatiProvimeve(){
        new DeleteAllProvimiAsyncTask(afatiProvimeveDao).execute();
    }

    public LiveData<List<AfatiProvimeve>> getAllProvimet(){
        return allProvimet;
    }

    private static class InsertProvimiAsyncTask extends AsyncTask<AfatiProvimeve,Void,Void>{
        private AfatiProvimeveDao afatiProvimeveDao;

        private InsertProvimiAsyncTask(AfatiProvimeveDao afatiProvimeveDao){
            this.afatiProvimeveDao = afatiProvimeveDao;
        }


        @Override
        protected Void doInBackground(AfatiProvimeve... afatiProvimeves) {
            afatiProvimeveDao.insert(afatiProvimeves[0]);
            return null;
        }
    }

    private static class UpdateProvimiAsyncTask extends AsyncTask<AfatiProvimeve,Void,Void>{
        private AfatiProvimeveDao afatiProvimeveDao;

        private UpdateProvimiAsyncTask(AfatiProvimeveDao afatiProvimeveDao){
            this.afatiProvimeveDao = afatiProvimeveDao;
        }


        @Override
        protected Void doInBackground(AfatiProvimeve... afatiProvimeves) {
            afatiProvimeveDao.update(afatiProvimeves[0]);
            return null;
        }
    }

    private static class DeleteProvimiAsyncTask extends AsyncTask<AfatiProvimeve,Void,Void>{
        private AfatiProvimeveDao afatiProvimeveDao;

        private DeleteProvimiAsyncTask(AfatiProvimeveDao afatiProvimeveDao){
            this.afatiProvimeveDao = afatiProvimeveDao;
        }


        @Override
        protected Void doInBackground(AfatiProvimeve... afatiProvimeves) {
            afatiProvimeveDao.delete(afatiProvimeves[0]);
            return null;
        }
    }

    private static class DeleteAllProvimiAsyncTask extends AsyncTask<Void,Void,Void>{
        private AfatiProvimeveDao afatiProvimeveDao;

        private DeleteAllProvimiAsyncTask(AfatiProvimeveDao afatiProvimeveDao){
            this.afatiProvimeveDao = afatiProvimeveDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            afatiProvimeveDao.deleteAllAfatiProvimeve();
            return null;
        }
    }

}
