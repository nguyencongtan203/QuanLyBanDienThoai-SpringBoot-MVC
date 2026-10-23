package com.example.QuanLyBanDienThoai.repository;

import com.example.QuanLyBanDienThoai.entity.Chitietcauhinh;
import com.example.QuanLyBanDienThoai.entity.ChitietcauhinhId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitietcauhinhRepository extends JpaRepository<Chitietcauhinh, ChitietcauhinhId> {
    // Hoặc nếu cần lấy nhiều RAM cùng lúc
    @Query("SELECT DISTINCT c.id.masp FROM Chitietcauhinh c WHERE c.id.mach = 5 AND c.giatri IN :ramValues")
    List<Integer> findMaspByMachAndGiatriIn(@Param("ramValues") List<String> ramValues);

    @Query("SELECT DISTINCT c.id.masp FROM Chitietcauhinh c WHERE c.id.mach = 6 AND c.giatri IN :storageValues")
    List<Integer> findMaspByMachAndGiatriStorages(@Param("storageValues") List<String> storageValues);

//    @Query("SELECT c FROM Chitietcauhinh c WHERE c.masp = :masp")
//    List<Chitietcauhinh> findByMasp(@Param("masp") int masp);
    List<Chitietcauhinh> findByMasp(int masp);
}
