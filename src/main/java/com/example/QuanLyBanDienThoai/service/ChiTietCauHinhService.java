package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Chitietcauhinh;
import com.example.QuanLyBanDienThoai.entity.Sanpham;
import com.example.QuanLyBanDienThoai.repository.ChitietcauhinhRepository;
import com.example.QuanLyBanDienThoai.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChiTietCauHinhService {
    @Autowired
    private ChitietcauhinhRepository chitietcauhinhRepository;
    @Autowired
    private SanphamRepository sanphamRepository;

    public List<Sanpham> getProductsByFilters(List<String> ramValues, List<String> storageValues, List<Integer> brandValues) {
        Set<Integer> resultMaSP = new HashSet<>();
        boolean hasFilter = false;

        // Lọc theo Brand
        if (brandValues != null && !brandValues.isEmpty()) {
            List<Sanpham> brandProducts = sanphamRepository.findByHangMahangIn(brandValues);

            resultMaSP.addAll(brandProducts.stream()
                    .map(Sanpham::getMasp)
                    .collect(Collectors.toSet()));
            hasFilter = true;
        }

        // Lọc theo RAM
        if (ramValues != null && !ramValues.isEmpty()) {
            List<Integer> ramProducts = chitietcauhinhRepository.findMaspByMachAndGiatriIn(ramValues);

            if (hasFilter) {
                resultMaSP.retainAll(new HashSet<>(ramProducts));
            } else {
                resultMaSP.addAll(ramProducts);
                hasFilter = true;
            }
        }

        // Lọc theo Storage
        if (storageValues != null && !storageValues.isEmpty()) {
            List<Integer> storageProducts = chitietcauhinhRepository.findMaspByMachAndGiatriStorages(storageValues);

            if (hasFilter) {
                resultMaSP.retainAll(new HashSet<>(storageProducts));
            } else {
                resultMaSP.addAll(storageProducts);
                hasFilter = true;
            }
        }

        if (!hasFilter) {
            return new ArrayList<>();
        }

        // Lấy thông tin chi tiết sản phẩm
        List<Sanpham> dsSanPham = new ArrayList<>();
        for (Integer masp : resultMaSP) {
            Optional<Sanpham> optionalSanpham = sanphamRepository.findById(masp);
            optionalSanpham.ifPresent(dsSanPham::add);
        }
        return dsSanPham;
    }

    public List<Chitietcauhinh> getChitietcauhinhByMasp(int masp) {
        return chitietcauhinhRepository.findByMasp(masp);
    }
}
