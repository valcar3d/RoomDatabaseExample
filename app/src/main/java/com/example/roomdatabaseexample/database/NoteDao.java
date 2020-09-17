package com.example.roomdatabaseexample.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.roomdatabaseexample.entities.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAllNotes();

    @Query("SELECT * FROM note WHERE mId LIKE :noteId")
    Note getNote(int noteId);

    @Insert
    void addNote(Note book);

    @Query("DELETE FROM note")
    void deleteAll();

    @Query("DELETE FROM note WHERE mId LIKE :noteId")
    void deleteNoteId(int noteId);

    @Query("UPDATE note SET message=:newMessage  WHERE mId =:noteId")
    void updateNote(String noteId, String newMessage);

}
