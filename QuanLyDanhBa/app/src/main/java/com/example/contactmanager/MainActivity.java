package com.example.contactmanager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.contactmanager.adapters.tabAdapter;
import com.google.android.material.tabs.TabLayout;

import java.sql.*;


public class MainActivity extends AppCompatActivity {
    Button btnAdd, btnSearch;
    SQLiteDatabase myDB;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    tabAdapter adapter;
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
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.viewPager2);
        adapter = new tabAdapter(this);
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
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
