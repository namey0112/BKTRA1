package com.example.contactmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_TAGS = "tags"; // Thêm cột tags

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_BOOKS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_AUTHOR + " TEXT, " +
                    COLUMN_TAGS + " TEXT" + // Thêm cột tags
                    ");";
    private static final String TABLE_CREATE_NHAN_VIEN = "CREATE TABLE IF NOT EXISTS nhan_vien (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ho_ten TEXT NOT NULL," +
            "chuc_vu TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "so_dien_thoai TEXT NOT NULL," +
            "anh_dai_dien BLOB," +
            "ma_don_vi INTEGER," + // Khóa ngoại
            "FOREIGN KEY (ma_don_vi) REFERENCES don_vi(id)" +
            ")";
    private static final String TABLE_CREATE_DON_VI = "CREATE TABLE IF NOT EXISTS don_vi (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ten_don_vi TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "website TEXT," +
            "logo BLOB," + // Lưu trữ ảnh dạng nhị phân (BLOB)
            "dia_chi TEXT NOT NULL," +
            "so_dien_thoai TEXT NOT NULL" +
            ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}
