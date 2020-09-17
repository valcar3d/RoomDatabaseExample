package com.example.roomdatabaseexample.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int mId;

    @ColumnInfo(name = "message")
    public String mMessage;


    public Note(String mMessage) {
        this.mMessage = mMessage;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    @Override
    public String toString() {
        return "Note{" +
                "mId='" + mId + '\'' +
                ", mMessage='" + mMessage + '\'' +
                '}';
    }
}
