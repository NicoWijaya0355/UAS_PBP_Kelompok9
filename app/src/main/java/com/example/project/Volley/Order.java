package com.example.project.Volley;

public class Order {
    String nama, telepon, alamat, total;

    public Order(String nama, String telepon, String alamat, String total) {
        this.nama = nama;
        this.telepon = telepon;
        this.alamat = alamat;
        this.total = total;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
