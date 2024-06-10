    package com.example.contactmanager.model;

    import android.graphics.Bitmap;

    public class DonVi {
        int id;
        String tenDv;
        String email;
        String website;
        byte[] logo;
        String diaChi;
        String dienThoai;

        public DonVi(int id, String tenDv, String email, String website, byte[] logo, String diaChi, String dienThoai) {
            this.id = id;
            this.tenDv = tenDv;
            this.email = email;
            this.website = website;
            this.logo = logo;
            this.diaChi = diaChi;
            this.dienThoai = dienThoai;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTenDv() {
            return tenDv;
        }

        public void setTenDv(String tenDv) {
            this.tenDv = tenDv;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public byte[] getLogo() {
            return logo;
        }

        public void setLogo(byte[] logo) {
            this.logo = logo;
        }

        public String getDiaChi() {
            return diaChi;
        }

        public void setDiaChi(String diaChi) {
            this.diaChi = diaChi;
        }

        public String getDienThoai() {
            return dienThoai;
        }

        public void setDienThoai(String dienThoai) {
            this.dienThoai = dienThoai;
        }
    }
