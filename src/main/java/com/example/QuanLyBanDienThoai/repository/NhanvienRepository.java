package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.entity.Nhanvien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanvienRepository extends JpaRepository<Nhanvien,Integer> {
    Optional<Nhanvien> findByTaikhoan(String taikhoan);
}
