package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Sanpham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanphamRepository extends JpaRepository<Sanpham,Integer> {
    @Query("SELECT s FROM Sanpham s WHERE s.hang.mahang IN :mahangList")
    List<Sanpham> findByHangMahangIn(@Param("mahangList") List<Integer> mahangList);

    @Query("SELECT s FROM Sanpham s WHERE LOWER(s.tensp) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Sanpham> findByTenspContainingIgnoreCase(@Param("keyword") String keyword);
}
