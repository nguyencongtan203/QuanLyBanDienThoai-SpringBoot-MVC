package com.example.QuanLyBanDienThoai.controller;


import com.example.QuanLyBanDienThoai.entity.*;
import com.example.QuanLyBanDienThoai.service.ChiTietCauHinhService;
import com.example.QuanLyBanDienThoai.service.ChiTietGioHangService;
import com.example.QuanLyBanDienThoai.service.DonHangService;
import com.example.QuanLyBanDienThoai.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    @Autowired
    private SanPhamService sanphamService;
    @Autowired
    private ChiTietGioHangService chiTietGioHangService;
    @Autowired
    private ChiTietCauHinhService chitietcauhinhService;
    @Autowired
    private DonHangService donHangService;

    // endpoint lấy chi tiết sản phẩm theo id sản phẩm
    @GetMapping("/product/{id}")
    public String getProductDetail(@PathVariable("id") int id, Model model) {
        Sanpham sanpham = sanphamService.getProductById(id);
        if (sanpham == null) {
            return "redirect:/error";
        }
        List<Chitietcauhinh> chitietcauhinhList = chitietcauhinhService.getChitietcauhinhByMasp(id);
        // Nhóm chi tiết cấu hình theo tên cấu hình
        Map<String, List<Chitietcauhinh>> groupedData = chitietcauhinhList.stream()
            .collect(Collectors.groupingBy(c -> c.getCauhinh().getTench()));

        List<Mausanpham> colors = sanphamService.getColorsByMasp(id);

        model.addAttribute("sanpham", sanpham);
        model.addAttribute("groupedData", groupedData);
        model.addAttribute("colors", colors);

        return "pages/product";
    }

    // endpoint xử lý thêm sản phẩm vào chi tiết giỏ hàng
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam(name = "quantity", required = false, defaultValue = "1") int quantity,
                            HttpSession session,
                            Model model) {
        try {
            Integer magh = (Integer) session.getAttribute("giohang");
            if (magh == null) {
                throw new Exception("Không tìm thấy giỏ hàng của người dùng.");
            }

            // Thêm sản phẩm vào giỏ hàng
            chiTietGioHangService.addToCart(magh, productId, quantity);
            // Hiển thị thông báo thành công
            model.addAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
            // Quay lại trang chi tiết sản phẩm
            return "redirect:/product/" + productId;
        } catch (Exception e) {
            model.addAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            return "error"; // Hiển thị trang lỗi
        }
    }

    // endpoint xóa sản phẩm khỏi chi tiết giỏ hàng
    @PostMapping("/remove")
    public String removeProductFromCart(@RequestParam("masp") int masp,
                                        HttpSession session,
                                        Model model){

        Integer magh = (Integer) session.getAttribute("giohang");
        if (magh == null) {
            return "error";
        }

        chiTietGioHangService.removeProductFromCart(magh, masp);
        List<Chitietgiohang> chitietgiohang = chiTietGioHangService.getAllCartDetailByIdCart(magh);
        model.addAttribute("chitietgiohang", chitietgiohang);
        model.addAttribute("subtotal", chiTietGioHangService.tinhTongTien(chitietgiohang));

        // Trả về fragment cập nhật ngay
        return "pages/cart_dropdown :: cartDropdown";
    }

    // endpoint xóa sản phẩm khỏi chi tiết giỏ hàng tại trang checkout
    @PostMapping("/remove-from-checkout")
    public String removeProductFromCheckOut(@RequestParam("masp") int masp,
                                        HttpSession session,
                                        Model model){

        Integer magh = (Integer) session.getAttribute("giohang");
        if (magh == null) {
            return "error";
        }

        chiTietGioHangService.removeProductFromCart(magh, masp);
        List<Chitietgiohang> chitietgiohang = chiTietGioHangService.getAllCartDetailByIdCart(magh);
        model.addAttribute("chitietgiohang", chitietgiohang);
        model.addAttribute("subtotal", chiTietGioHangService.tinhTongTien(chitietgiohang));

        return "pages/checkout";
    }

    // endpoint cập nhật số lượng sản phẩm
    @PostMapping("/update-quantity")
    public String updateProductQuantity(@RequestParam("masp") int masp,
                                        @RequestParam("action") String action,
                                        HttpSession session) {
        Integer magh = (Integer) session.getAttribute("giohang");
        if (magh != null) {
            chiTietGioHangService.updateQuantity(magh, masp, action);
        }
        return "redirect:/checkout"; // redirect để toàn bộ view được render lại
    }

    // endpoint xử lý đặt hàng
    @PostMapping("/checkout/place-order")
    public String placeOrder(@RequestParam("address") String address,
                            @RequestParam("tel") String tel,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        try {
            Integer magh = (Integer) session.getAttribute("giohang");

            if (magh == null) {
                throw new Exception("Thông tin khách hàng hoặc giỏ hàng không hợp lệ.");
            }

            donHangService.placeOrder(magh, address, tel);
            redirectAttributes.addFlashAttribute("success", "Đặt hàng thành công!");
            return "redirect:/checkout";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Đặt hàng thất bại: " + e.getMessage());
            return "redirect:/checkout";
        }
    }

}
