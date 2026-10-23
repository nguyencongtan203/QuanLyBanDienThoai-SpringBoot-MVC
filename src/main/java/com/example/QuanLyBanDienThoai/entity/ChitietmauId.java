package com.example.QuanLyBanDienThoai.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChitietmauId implements Serializable {
    private int masp;
    private int mamau;

    public ChitietmauId() {
    }

    public ChitietmauId(int masp, int mamau) {
        this.masp = masp;
        this.mamau = mamau;
    }

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

    // Override equals và hashCode để đảm bảo so sánh chính xác
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChitietmauId that = (ChitietmauId) o;
        return Objects.equals(masp, that.masp) && Objects.equals(mamau, that.mamau);
    }

    @Override
    public int hashCode() {
        return Objects.hash(masp, mamau);
    }
}
