package com.example.mydeezer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQHelper extends SQLiteOpenHelper {
    static final String DATABASE_NAME="app.db";

    static final String TABLE_NAME_SONG="song";
    static final  int VERSION_CODE=1;
    public SQHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public SQHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+TABLE_NAME_SONG+"(id,title,duration,album_title,cover,collection)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}