package com.example.QuanLyBanDienThoai.controller;

import com.example.QuanLyBanDienThoai.dto.request.KhachHangCreationRequest;
import com.example.QuanLyBanDienThoai.dto.request.KhachHangLoginRequest;
import com.example.QuanLyBanDienThoai.entity.Khachhang;
import com.example.QuanLyBanDienThoai.service.GioHangService;
import com.example.QuanLyBanDienThoai.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class KhachHangController {
    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private GioHangService gioHangService;

    //endpoint danh sách khách hàng
    @GetMapping("/findall")
    public String getAllKhachHang(Model model,@ModelAttribute("name") String name) {
        try {
            List<Khachhang> list = khachHangService.getAllKhachHang();
            model.addAttribute("khachhangList", list);
            if (name != null) {
                model.addAttribute("name", name);
            }
            return "pages/khachhangList";
        } catch (Exception e) {
            model.addAttribute("message", "Error retrieving list account: " + e.getMessage());
            return "error";
        }
    }

    //endpoint hiển thị trang chủ
    @GetMapping("/home")
    public String showHome(){
        return "pages/index";
    }

    //endpoint đăng nhập google
    @GetMapping("/login")
    public String loginPage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        return "pages/login";
    }

    //endpoint xử lý đăng nhập thường
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        KhachHangLoginRequest request = new KhachHangLoginRequest(email, password);

        if (khachHangService.loginAccount(request)) {
            return "redirect:/home";
        } else {
            model.addAttribute("login_error", "Tài khoản hoặc mật khẩu không đúng!");
            return "pages/login";
        }
    }

    //endpoint xử lý đăng ký
    @PostMapping("/register")
    public String register(@ModelAttribute KhachHangCreationRequest request, Model model) {
        String message = khachHangService.registerAccount(request);

        if (message.equals("Đăng ký thành công!")) {
            gioHangService.createGioHang(khachHangService.findByEmail(request.getEmail()).get().getMakh());
            return "redirect:/login";
        } else {
            model.addAttribute("register_error", message);
            return "pages/login";
        }
    }
}
