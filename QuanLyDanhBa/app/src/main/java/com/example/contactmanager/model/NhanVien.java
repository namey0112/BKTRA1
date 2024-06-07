package com.example.contactmanager.model;

import android.graphics.Bitmap;

public class NhanVien {
    int id;
    String hoTen;
    String chucVu;
    String dienThoai;
    String email;
    Bitmap anhDaiDien;
    int maDonVi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(Bitmap anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public int getMaDonVi() {
        return maDonVi;
    }

    public void setMaDonVi(int maDonVi) {
        this.maDonVi = maDonVi;
    }
}
