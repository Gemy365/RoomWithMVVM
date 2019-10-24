// Check Structure of Room from res/drawable/room_structure.jpg
package com.example.android.RoomWithMVVM;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

// DAO >> Data Access Object.
// Connect With Note.java Class
@Dao
public interface NoteDao {

    // Query To Get All Data.
    // LiveData.. Be aware to any change will be happen for List<Note>.
    @Query("SELECT * FROM note_table ORDER BY priority_column DESC")
    LiveData<List<Note>> getAllNote();

    // For Insert.
    @Insert
    void insert(Note note);

    // For Update.
    @Update
    void update(Note note);

    // For Delete.
    @Delete
    void delete(Note note);

    // Query For Delete All Info.
    @Query("DELETE FROM note_table")
    void deleteAllNotes();
}
