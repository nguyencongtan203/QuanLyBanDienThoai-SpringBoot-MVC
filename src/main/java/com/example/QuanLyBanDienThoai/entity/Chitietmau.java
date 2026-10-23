package com.example.QuanLyBanDienThoai.entity;

import jakarta.persistence.*;

@Entity
@IdClass(ChitietmauId.class)
public class Chitietmau {
    @Id
    @Column(name = "masp")
    private int masp;

    @Id
    @Column(name = "mamau")
    private int mamau;

    @ManyToOne
    @JoinColumn(name = "masp", insertable = false, updatable = false)
    private Sanpham sanPham;

    @ManyToOne
    @JoinColumn(name = "mamau", insertable = false, updatable = false)
    private Mausanpham mauSanPham;

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMamau() {
        return mamau;
    }

    public void setMamau(int mamau) {
        this.mamau = mamau;
    }

    public Sanpham getSanPham() {
        return sanPham;
    }

    public void setSanPham(Sanpham sanPham) {
        this.sanPham = sanPham;
    }

    public Mausanpham getMauSanPham() {
        return mauSanPham;
    }

    public void setMauSanPham(Mausanpham mauSanPham) {
        this.mauSanPham = mauSanPham;
    }
}
