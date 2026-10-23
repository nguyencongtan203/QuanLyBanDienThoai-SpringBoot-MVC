package com.example.QuanLyBanDienThoai.controller;

import com.example.QuanLyBanDienThoai.core.FilterRequest;
import com.example.QuanLyBanDienThoai.dto.request.SanphamDTO;
import com.example.QuanLyBanDienThoai.entity.Sanpham;
import com.example.QuanLyBanDienThoai.service.ChiTietCauHinhService;
import com.example.QuanLyBanDienThoai.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StoreController {
    @Autowired
    private ChiTietCauHinhService chiTietCauHinhService;
    @Autowired
    private SanPhamService sanPhamService;

    // endpoint lưu thông tin bộ lọc do người dùng chọn vào session
    @PostMapping("/store")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> filterStore(@RequestBody FilterRequest request, HttpSession session) {
        try {
            // Lưu filter vào session
            session.setAttribute("storeFilters", request);

            // Trả về response JSON
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("redirect", "/store");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Error processing request");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // endpoint lấy thông tin bộ lọc từ session + hiện thị trang store
    @GetMapping("/store")
    public String getStorePage(HttpSession session, Model model) {
        try {
            // Lấy filter từ session
            FilterRequest filters = (FilterRequest) session.getAttribute("storeFilters");

            if (filters != null && !filters.getBrands().isEmpty()) {
                // Lấy sản phẩm theo filter
                List<Sanpham> filteredProducts = chiTietCauHinhService.getProductsByFilters(
                        filters.getRams(),
                        filters.getStorages(),
                        filters.getBrands()
                );

                model.addAttribute("products", filteredProducts);
                model.addAttribute("selectedBrand", filters.getBrands().get(0));

                // Xóa filter khỏi session sau khi sử dụng
                session.removeAttribute("storeFilters");
            }

            // Thêm các attribute khác
            return "pages/store";
        } catch (Exception e) {
            return "error";
        }
    }

    // endpoint xử lý bộ lọc
    @PostMapping("/store/filter")
    @ResponseBody
    public ResponseEntity<?> filter(@RequestBody FilterRequest request) {

        try {
            // kiểm tra request
            if ((request.getRams() == null || request.getRams().isEmpty())
                    && (request.getStorages() == null || request.getStorages().isEmpty())
                    && (request.getBrands() == null || request.getBrands().isEmpty())) {

                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("error", "Vui lòng chọn ít nhất một tiêu chí lọc");
                return ResponseEntity.badRequest().body(errorMap);
            }

            // Lấy danh sách sản phẩm theo bộ lọc
            List<Sanpham> filteredProducts = chiTietCauHinhService.getProductsByFilters(
                    request.getRams(),
                    request.getStorages(),
                    request.getBrands()
            );

            // Tạo response
            Map<String, Object> response = new HashMap<>();
            response.put("products", filteredProducts.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()));

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Có lỗi xảy ra khi lọc sản phẩm");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }

    // chuyển entity thành dto để lấy những thông tin cần thiết
    private SanphamDTO convertToDTO(Sanpham sanpham) {
        SanphamDTO dto = new SanphamDTO();
        dto.setMasp(sanpham.getMasp());
        dto.setTensp(sanpham.getTensp());
        dto.setGia(sanpham.getGia());
        dto.setHinhanh(sanpham.getHinhanh());
        if (sanpham.getHang() != null) {
            dto.setTenhang(sanpham.getHang().getTenhang());
        }
        return dto;
    }

    // endpoint xử lý tìm kiếm
    @GetMapping("/store/search")
    public ResponseEntity<?> searchProducts(
            @RequestParam(required = false, defaultValue = "") String keyword) {

        // Lấy kết quả từ service
        Map<String, Object> result = sanPhamService.searchProducts(keyword);

        // Kiểm tra lỗi
        if (result.containsKey("error")) {
            return ResponseEntity.badRequest().body(result);
        }

        // Convert sang DTO nếu có products
        if (result.containsKey("products")) {
            List<Sanpham> products = (List<Sanpham>) result.get("products");
            List<SanphamDTO> dtoList = products.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());

            // Tạo response mới với DTO
            Map<String, Object> dtoResponse = new HashMap<>();
            dtoResponse.put("products", dtoList);
            dtoResponse.put("total", dtoList.size());
            dtoResponse.put("keyword", result.get("keyword"));

            return ResponseEntity.ok(dtoResponse);
        }

        // Trả về response gốc nếu không có products
        return ResponseEntity.ok(result);
    }
}
