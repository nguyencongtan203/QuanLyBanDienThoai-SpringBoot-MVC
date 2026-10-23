package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Chitietdonhang;
import com.example.QuanLyBanDienThoai.entity.ChitietdonhangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitietdonhangRepository extends JpaRepository<Chitietdonhang, ChitietdonhangId> {
    List<Chitietdonhang> findByMadh(int madh);
}
