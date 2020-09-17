package com.example.roomdatabaseexample.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.roomdatabaseexample.entities.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static volatile NoteDatabase instance;
    public abstract NoteDao NoteDao();

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            //Make thread safe check
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "notes_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }

        }
        return instance;
    }


}
