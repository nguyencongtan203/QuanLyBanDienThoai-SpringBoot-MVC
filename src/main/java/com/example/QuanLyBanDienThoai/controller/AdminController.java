package com.example.QuanLyBanDienThoai.controller;

import com.example.QuanLyBanDienThoai.dto.request.NhanVienLoginRequest;
import com.example.QuanLyBanDienThoai.entity.Chitietdonhang;
import com.example.QuanLyBanDienThoai.entity.Donhang;
import com.example.QuanLyBanDienThoai.service.ChiTietDonHangService;
import com.example.QuanLyBanDienThoai.service.DonHangService;
import com.example.QuanLyBanDienThoai.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDonHangService chiTietDonHangService;

    //endpoint hiển thị trang đăng nhập admin
    @GetMapping("/login")
    public String showAdminLoginPage() {
        return "pages/admin_login";
    }

    //endpoint xử lý đăng nhập admin
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("pass") String password,
                        Model model) {
        NhanVienLoginRequest nhanVienLoginRequest = new NhanVienLoginRequest(email, password);
        if (nhanVienService.loginAccount(nhanVienLoginRequest)) {
            return "pages/admin_page";
        } else {
            model.addAttribute("login_error", "Tài khoản hoặc mật khẩu không đúng!");
            return "pages/admin_login";
        }
    }

    //endpoint lấy danh sách đơn hàng
    @GetMapping("/admin-dashboard")
    public String getAllDonHang(Model model) {
        List<Donhang> donhangs = donHangService.getAllDonHang();
        model.addAttribute("listOrder", donhangs);
        Map<Integer, List<Chitietdonhang>> detailList = new HashMap<>();
        for (Donhang order : donhangs) {
            List<Chitietdonhang> orderDetails = chiTietDonHangService.getChiTietDonHangByMaDh(order.getMadh());
            detailList.put(order.getMadh(), orderDetails);
        }
        model.addAttribute("detailList", detailList);
        return "pages/admin_page";
    }

    //endpoint xử lý xác nhận đơn hàng
    @PostMapping("/order/submit")
    public String cancelOrder(@RequestParam("madh") int madh, RedirectAttributes redirectAttributes) {
        boolean success = donHangService.submitOrder(madh);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đã xác nhận đơn hàng " + madh);
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xác nhận đơn hàng " + madh);
        }
        return "redirect:/admin/admin-dashboard";
    }

    //endpoint xử lý xác nhận thanh toán đơn hàng
    @PostMapping("/order/complete")
    public String completeOrder(@RequestParam("madh") int madh, RedirectAttributes redirectAttributes) {
        boolean success = donHangService.submitOrderComplete(madh);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đã xác nhận thanh toán đơn hàng " + madh);
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xác nhận đơn hàng " + madh);
        }
        return "redirect:/admin/admin-dashboard";
    }

    //endpoint xác nhận hoàn đơn hàng
    @PostMapping("/order/refund")
    public String refundOrder(@RequestParam("madh") int madh, RedirectAttributes redirectAttributes) {
        boolean success = donHangService.submitOrderRefund(madh);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đã xác nhận hoàn đơn hàng " + madh);
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể xác nhận đơn hàng " + madh);
        }
        return "redirect:/admin/admin-dashboard";
    }
}
