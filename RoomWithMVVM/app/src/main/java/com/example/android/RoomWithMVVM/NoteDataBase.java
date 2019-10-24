// Check Structure of Room from res/drawable/room_structure.jpg
package com.example.android.RoomWithMVVM;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

// @Database Connect Database For NoteDao Interface & Note.Class
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    // Object from NoteDataBase class to use it more than one time.
    private static NoteDataBase instance;

    // abstract method from NoteDao Interface.
    public abstract NoteDao noteDao();

    // Instance of Room DataBase.
    // synchronized allows one thread only at the time.
    public static synchronized NoteDataBase getInstance(Context context) {
        // if instance is null
        if (instance == null) {
            // Build room database.
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    // Write name for NoteDataBase.class.
                    NoteDataBase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    // Work with RoomDatabase.Callback.
                    .addCallback(roomCallback)
                    // build.
                    .build();
        }
        // return.
        return instance;
    }

    // Create roomCallback var of RoomDatabase.Callback.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Execute PopulateDbAsyncTask class & send instance as param for constructor.
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // PopulateDbAsyncTask Class work into background to make Init data stored into Room database.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{

        // Object from NoteDao Interface.
        private NoteDao noteDao;

        // Constructor.
        private PopulateDbAsyncTask(NoteDataBase db){
            // From NoteDataBase class Call noteDao abstract method.
            noteDao = db.noteDao();
        }

        // Background worker.
        @Override
        protected Void doInBackground(Void... voids) {
            // From NoteDao Interface Use Insert For Fake or init data.
            noteDao.insert(new Note("Title1", "Description1", 1));
            noteDao.insert(new Note("Title2", "Description2", 2));
            noteDao.insert(new Note("Title3", "Description3", 3));
            return null;
        }
    }
}
