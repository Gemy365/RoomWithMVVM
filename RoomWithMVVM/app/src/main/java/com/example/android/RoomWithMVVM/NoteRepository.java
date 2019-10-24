// Check Structure of Room from res/drawable/room_structure.jpg
package com.example.android.RoomWithMVVM;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    // Object from NoteDao Interface.
    private NoteDao noteDao;
    // Observe Data by using LiveData.
    private LiveData<List<Note>> allNotes;

    // Constructor.
    public NoteRepository(Application application){
        // Call getInstance method from NoteDataBase Class.
        NoteDataBase dataBase = NoteDataBase.getInstance(application);
        // Call noteDao() abstract method from NoteDataBase Class.
        noteDao = dataBase.noteDao();
        // Call getAllNote() method from NoteDao Interface.
        allNotes = noteDao.getAllNote();
    }

    // Insert method.
    public void insert(Note note){
        // Work With InsertNoteAsyncTask To work into background worker.
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    // Update method.
    public void update(Note note){
        // Work With InsertNoteAsyncTask To work into background worker.
        new UpdateNoteAsyncTask(noteDao).execute(note);

    }

    // Delete method.
    public void delete(Note note){
        // Work With InsertNoteAsyncTask To work into background worker.
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }

    // DeleteAllNotes method.
    public void deleteAllNotes(){
        // Work With InsertNoteAsyncTask To work into background worker.
        new DeleteAllNotesAsyncTask(noteDao).execute();

    }

    // Get All Notes From LiveData.
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    // Background worker for insert.
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        public InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    // Background worker for update.
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        public UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    // Background worker for delete.
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void>{
        private NoteDao noteDao;

        public DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    // Background worker for delete.
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao noteDao;

        public DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
