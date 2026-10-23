package com.example.QuanLyBanDienThoai.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Mausanpham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mamau;

    @OneToMany(mappedBy = "mauSanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Chitietmau> chiTietMau;

    private String giatri;

    public int getMamau() {
        return mamau;
    }

    public void setMamau(int mamau) {
        this.mamau = mamau;
    }

    public String getGiatri() {
        return giatri;
    }

    public void setGiatri(String giatri) {
        this.giatri = giatri;
    }

    public List<Chitietmau> getChiTietMau() {
        return chiTietMau;
    }

    public void setChiTietMau(List<Chitietmau> chiTietMau) {
        this.chiTietMau = chiTietMau;
    }
}
