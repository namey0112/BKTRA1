package com.example.contactmanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.sql.*;


public class MainActivity extends AppCompatActivity {

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
    }
    public void TaoBangSQLite () {
        String url = "jdbc:sqlite:du_lieu.db"; // Tên file cơ sở dữ liệu

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

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
            stmt.execute(sqlDonVi);

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
            stmt.execute(sqlNhanVien);

            System.out.println("Tạo bảng thành công!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
