// Check Structure of Room from res/drawable/room_structure.jpg
package com.example.android.RoomWithMVVM;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// @Entity allows you to create table name into Room.
@Entity(tableName = "note_table")
public class Note {

    // @PrimaryKey Make ID PrimaryKey & Auto Generate [Auto Increment].
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String description;

    // @ColumnInfo Change name of column from 'priority' to 'priority_column'
    @ColumnInfo(name = "priority_column")
    private int priority;

    // Constructor.
    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // Getter & Setter.
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}
