package com.example.eafatiprovimeve.Data;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.eafatiprovimeve.ViewModel.AfatiProvimeve;

@Database(entities = {AfatiProvimeve.class},version = 2,exportSchema = false)
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
            //metoda(String)
            afatiProvimeveDao.insert(new AfatiProvimeve("Data Mining","Year: III","Semester: VI", "Day: Tuesday#Week: Second",8,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("MM","Year: III","Semester: V", "Day: Thursday#Week: Second",10,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("DIST","Year: III","Semester: VI", "Day: Wednesday#Week: Fourth",22,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("Elektronik","Year: II","Semester: III", "Day: Friday#Week: Fourth",25,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("Sinjalet dhe Sistemet","Year: II","Semester: III", "Day: Wednesday#Week: Third",16,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("Praktika profesionale","Year: III","Semester: VI", "Day: Wednesday#Week: Fourth",23,""));
            afatiProvimeveDao.insert(new AfatiProvimeve("Programimi Paisjeve Mobile","Year: III","Semester: VI", "Day: Monday#Week: Fourth",21,""));
            return null;
        }
    }
}
