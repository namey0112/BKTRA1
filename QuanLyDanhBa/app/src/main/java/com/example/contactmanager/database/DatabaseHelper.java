package com.example.contactmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contact.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NHAN_VIEN = "nhan_vien";
    public static final String TABLE_DON_VI = "don_vi";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME_DON_VI = "ten_don_vi";
    public static final String COLUMN_EMAIL_DON_VI = "email";
    public static final String COLUMN_WEBSITE_DON_VI = "website";
    public static final String COLUMN_LOGO_DON_VI = "logo";
    public static final String COLUMN_DIA_CHI_DON_VI = "dia_chi";
    public static final String COLUMN_SO_DIEN_THOAI_DON_VI = "so_dien_thoai";
    public static final String COLUMN_HO_TEN = "ho_ten";
    public static final String COLUMN_CHUC_VU = "chuc_vu";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SO_DIEN_THOAI = "so_dien_thoai";
    public static final String COLUMN_ANH_DAI_DIEN = "anh_dai_dien";
    public static final String COLUMN_MA_DON_VI = "ma_don_vi";

    private static final String TABLE_CREATE_NHAN_VIEN =
            "CREATE TABLE IF NOT EXISTS "+ TABLE_NHAN_VIEN +" (" +
                    COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_HO_TEN + "TEXT NOT NULL," +
                    COLUMN_CHUC_VU + "TEXT NOT NULL," +
                    COLUMN_EMAIL + "TEXT NOT NULL," +
                    COLUMN_SO_DIEN_THOAI + "TEXT NOT NULL," +
                    COLUMN_ANH_DAI_DIEN + "BLOB," +
                    COLUMN_MA_DON_VI + "ma_don_vi INTEGER," +
                    "FOREIGN KEY (ma_don_vi) REFERENCES don_vi(id)" +
            ")";
    private static final String TABLE_CREATE_DON_VI =
            "CREATE TABLE IF NOT EXISTS "+ TABLE_DON_VI +" (" +
                    COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_DON_VI + "TEXT NOT NULL," +
                    COLUMN_EMAIL_DON_VI +"TEXT NOT NULL," +
                    COLUMN_WEBSITE_DON_VI +"TEXT," +
                    COLUMN_LOGO_DON_VI +"BLOB," + // Lưu trữ ảnh dạng nhị phân (BLOB)
                    COLUMN_DIA_CHI_DON_VI +"TEXT NOT NULL," +
                    COLUMN_SO_DIEN_THOAI_DON_VI +"TEXT NOT NULL" +
            ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_DON_VI);
        db.execSQL(TABLE_CREATE_NHAN_VIEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHAN_VIEN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DON_VI);
        onCreate(db);
    }
}
