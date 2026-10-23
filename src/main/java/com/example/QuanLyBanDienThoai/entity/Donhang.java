package com.example.QuanLyBanDienThoai.entity;

import com.example.QuanLyBanDienThoai.core.TrangThaiDonHang;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
@Entity
public class Donhang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int madh;

    @ManyToOne
    @JoinColumn(name = "makh")
    private Khachhang khachHang;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Hoadon> hoaDon;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Chitietdonhang> chiTietDonHang;

    private TrangThaiDonHang trangthai;
    private Timestamp ngaytao;
    private BigDecimal tongtien;
    private String diachi;
    private String sdt;

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public List<Hoadon> getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(List<Hoadon> hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Khachhang getKhachhang() {
        return khachHang;
    }

    public void setKhachhang(Khachhang khachhang) {
        this.khachHang = khachhang;
    }

    public List<Chitietdonhang> getChiTietDonHang() {
        return chiTietDonHang;
    }

    public void setChiTietDonHang(List<Chitietdonhang> chiTietDonHang) {
        this.chiTietDonHang = chiTietDonHang;
    }

    public int getMadh() {
        return madh;
    }

    public void setMadh(int madh) {
        this.madh = madh;
    }

    public TrangThaiDonHang getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(TrangThaiDonHang trangthai) {
        this.trangthai = trangthai;
    }

    public Timestamp getNgaytao() {
        return ngaytao;
    }

    public void setNgaytao(Timestamp ngaytao) {
        this.ngaytao = ngaytao;
    }

    public BigDecimal getTongtien() {
        return tongtien;
    }

    public void setTongtien(BigDecimal tongtien) {
        this.tongtien = tongtien;
    }
}
