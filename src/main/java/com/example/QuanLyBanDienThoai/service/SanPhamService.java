package com.example.QuanLyBanDienThoai.service;

import com.example.QuanLyBanDienThoai.entity.Chitietmau;
import com.example.QuanLyBanDienThoai.entity.Mausanpham;
import com.example.QuanLyBanDienThoai.entity.Sanpham;
import com.example.QuanLyBanDienThoai.repository.ChitietmauRepository;
import com.example.QuanLyBanDienThoai.repository.SanphamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SanPhamService {
    @Autowired
    private SanphamRepository sanphamRepository;
    @Autowired
    private ChitietmauRepository chitietmauRepository;

    public List<Sanpham> getAllProducts(){
        return sanphamRepository.findAll();
    }

    public Map<String, Object> searchProducts(String keyword) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (keyword == null) {
                response.put("error", "Từ khóa tìm kiếm không được để trống");
                return response;
            }

            String searchKeyword = keyword.trim();

            if (searchKeyword.isEmpty()) {
                response.put("products", sanphamRepository.findAll());
                return response;
            }

            List<Sanpham> results = sanphamRepository.findByTenspContainingIgnoreCase(searchKeyword);

            if (results.isEmpty()) {
                response.put("message", "Không tìm thấy sản phẩm phù hợp");
                response.put("keyword", searchKeyword);
                return response;
            }

            // Trả về kết quả
            response.put("products", results);
            response.put("total", results.size());
            response.put("keyword", searchKeyword);

        } catch (Exception e) {
            response.put("error", "Có lỗi xảy ra khi tìm kiếm sản phẩm");
        }

        return response;
    }

    public Sanpham getProductById(int masp) {
        return sanphamRepository.findById(masp).orElse(null);
    }
    public List<Mausanpham> getColorsByMasp(int masp) {
        return chitietmauRepository.findColorsByMasp(masp);
    }
}
