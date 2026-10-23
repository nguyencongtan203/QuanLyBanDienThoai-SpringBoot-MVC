package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Chitietgiohang;
import com.example.QuanLyBanDienThoai.entity.ChitietgiohangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChitietgiohangRepository extends JpaRepository<Chitietgiohang, ChitietgiohangId> {
    Optional<Chitietgiohang> findByMaghAndMasp(int magh, int masp);
    List<Chitietgiohang> findByMagh(int magh);
}
