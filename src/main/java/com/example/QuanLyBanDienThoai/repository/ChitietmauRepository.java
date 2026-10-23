package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Chitietmau;
import com.example.QuanLyBanDienThoai.entity.ChitietmauId;
import com.example.QuanLyBanDienThoai.entity.Mausanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitietmauRepository extends JpaRepository<Chitietmau, ChitietmauId> {
        @Query("SELECT DISTINCT m FROM Mausanpham m JOIN Chitietmau cm ON m.mamau = cm.mamau WHERE cm.masp = :masp")
        List<Mausanpham> findColorsByMasp(@Param("masp") int masp);
        List<Chitietmau> findByMasp(int masp);
}
