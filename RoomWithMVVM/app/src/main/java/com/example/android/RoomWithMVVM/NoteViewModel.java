package com.example.android.RoomWithMVVM;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    // Var from NoteRepository class.
    private NoteRepository repository;
    // Observe Data by using LiveData.
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        // Constructor of NoteRepository class.
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    // Call method insert from NoteRepository class.
    public void insert(Note note){
        repository.insert(note);
    }

    // Call method update from NoteRepository class.
    public void update(Note note){
        repository.update(note);
    }

    // Call method delete from NoteRepository class.
    public void delete(Note note){
        repository.delete(note);
    }

    // Call method deleteAllNotes from NoteRepository class.
    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    // Get All Notes From LiveData.
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

}
