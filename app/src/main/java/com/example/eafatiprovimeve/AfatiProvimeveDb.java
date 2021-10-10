package com.example.eafatiprovimeve;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AfatiProvimeve.class},version = 1,exportSchema = false)
public abstract class AfatiProvimeveDb extends RoomDatabase {

    private static AfatiProvimeveDb instance;

    public abstract AfatiProvimeveDao afatiProvimeveDao();

    public static synchronized AfatiProvimeveDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AfatiProvimeveDb.class,"afatiprovimeve_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private AfatiProvimeveDao afatiProvimeveDao;

        private PopulateDbAsyncTask(AfatiProvimeveDb db){
            afatiProvimeveDao = db.afatiProvimeveDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            afatiProvimeveDao.insert(new AfatiProvimeve("Data Mining",3,6, "M2",8));
            afatiProvimeveDao.insert(new AfatiProvimeve("MM",3,5, "E2",10));
            afatiProvimeveDao.insert(new AfatiProvimeve("DIST",3,6, "M4",22));
            afatiProvimeveDao.insert(new AfatiProvimeve("Elektronik",2,3, "P4",25));
            afatiProvimeveDao.insert(new AfatiProvimeve("Sinjalet dhe Sistemet",2,3, "MK3",16));
            afatiProvimeveDao.insert(new AfatiProvimeve("Praktika profesionale",2,3, "MK4",23));
            afatiProvimeveDao.insert(new AfatiProvimeve("Programimi Paisjeve Mobile",2,3, "H4",21));
            return null;
        }
    }
}
