package com.example.QuanLyBanDienThoai.controller;

import com.example.QuanLyBanDienThoai.core.SessionInfo;
import com.example.QuanLyBanDienThoai.entity.Chitietgiohang;
import com.example.QuanLyBanDienThoai.entity.Hang;
import com.example.QuanLyBanDienThoai.entity.Sanpham;
import com.example.QuanLyBanDienThoai.service.ChiTietGioHangService;
import com.example.QuanLyBanDienThoai.service.HangService;
import com.example.QuanLyBanDienThoai.service.SanPhamService;
import com.example.QuanLyBanDienThoai.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private HangService hangService;
    @Autowired
    private SanPhamService sanPhamService;
    @Autowired
    private ChiTietGioHangService chiTietGioHangService;

    @Autowired
    private SessionService sessionService;

    @ModelAttribute
    public void addGlobalAttributes(Model model,
                                    HttpServletRequest request,
                                    @CookieValue(name = "USER_SESSION", required = false) String userSessionId,
                                    @CookieValue(name = "ADMIN_SESSION", required = false) String adminSessionId) {

        try {
            // Lấy danh sách hãng và sản phẩm (dùng chung cho cả admin và user)
            List<Hang> hangList = hangService.getAllBrands();
            List<Sanpham> sanphamList = sanPhamService.getAllProducts();
            model.addAttribute("hangList", hangList);
            model.addAttribute("sanphamList", sanphamList);

            // Thời gian hiện tại UTC
            model.addAttribute("currentDateTime", LocalDateTime.now(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // Xử lý session user
            if (userSessionId != null) {
                SessionInfo userSession = sessionService.getUserSession(userSessionId);
                if (userSession != null) {
                    // Cập nhật thời gian truy cập
                    userSession.updateLastAccessTime();

                    // Thêm thông tin user vào model
                    model.addAttribute("email", userSession.getEmail());
                    model.addAttribute("name", userSession.getName());
                    model.addAttribute("phone_number", userSession.getPhoneNumber());
                    model.addAttribute("locale", userSession.getAddress());

                    // Xử lý giỏ hàng
                    if (userSession.getCartId() != null) {
                        try {
                            Integer magh = Integer.parseInt(userSession.getCartId());
                            List<Chitietgiohang> chitietgiohang =
                                    chiTietGioHangService.getAllCartDetailByIdCart(magh);

                            // Tính tổng giá
                            double subtotal = chitietgiohang.stream()
                                    .mapToDouble(item -> item.getThanhtien().doubleValue())
                                    .sum();

                            model.addAttribute("chitietgiohang", chitietgiohang);
                            model.addAttribute("subtotal", subtotal);
                        } catch (NumberFormatException e) {
                            model.addAttribute("chitietgiohang", Collections.emptyList());
                            model.addAttribute("subtotal", 0.0);
                        }
                    } else {
                        model.addAttribute("chitietgiohang", Collections.emptyList());
                        model.addAttribute("subtotal", 0.0);
                    }
                }
            } else {
                // Nếu không có session user, thêm giỏ hàng rỗng
                model.addAttribute("chitietgiohang", Collections.emptyList());
                model.addAttribute("subtotal", 0.0);
            }

            // Xử lý session admin
            if (adminSessionId != null) {
                SessionInfo adminSession = sessionService.getAdminSession(adminSessionId);
                if (adminSession != null) {
                    // Cập nhật thời gian truy cập
                    adminSession.updateLastAccessTime();

                    // Thêm thông tin admin vào model
                    model.addAttribute("taikhoan", adminSession.getEmail());
                    model.addAttribute("tennv", adminSession.getName());
                }
            }

        } catch (Exception e) {
            // Thêm giá trị mặc định khi có lỗi
            model.addAttribute("chitietgiohang", Collections.emptyList());
            model.addAttribute("subtotal", 0.0);
            model.addAttribute("hangList", Collections.emptyList());
            model.addAttribute("sanphamList", Collections.emptyList());
        }
    }
}
