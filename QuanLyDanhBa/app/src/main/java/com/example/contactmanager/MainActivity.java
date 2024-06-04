package com.example.contactmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.sql.*;


public class MainActivity extends AppCompatActivity {
    ImageButton btnSearch;
    SQLiteDatabase myDB;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TaoBangSQLite();
        btnSearch = findViewById(R.id.ibtnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to search activity
                Intent intent = new Intent(MainActivity.this, searchDv.class);
                startActivity(intent);
                finish();
            }
        });
        lv = findViewById(R.id.lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, staffManager.class);
                intent.putExtra("idDv", id);
                startActivity(intent);
                finish();
            }
        });
        String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

// Tạo ArrayAdapter và liên kết với ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        lv.setAdapter(adapter);
    }
    public void TaoBangSQLite () {
        myDB = openOrCreateDatabase("contact.db", MODE_PRIVATE, null);

        try {

            // Tạo bảng don_vi
            String sqlDonVi = "CREATE TABLE IF NOT EXISTS don_vi (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ten_don_vi TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "website TEXT," +
                    "logo BLOB," + // Lưu trữ ảnh dạng nhị phân (BLOB)
                    "dia_chi TEXT NOT NULL," +
                    "so_dien_thoai TEXT NOT NULL" +
                    ")";
            myDB.execSQL(sqlDonVi);


            // Tạo bảng nhan_vien
            String sqlNhanVien = "CREATE TABLE IF NOT EXISTS nhan_vien (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ho_ten TEXT NOT NULL," +
                    "chuc_vu TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "so_dien_thoai TEXT NOT NULL," +
                    "anh_dai_dien BLOB," +
                    "ma_don_vi INTEGER," + // Khóa ngoại
                    "FOREIGN KEY (ma_don_vi) REFERENCES don_vi(id)" +
                    ")";
            myDB.execSQL(sqlNhanVien);
            Log.e("Thông báo", "Table đã tạo thành công");

        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }
    }
}
