package com.example.QuanLyBanDienThoai.controller;

import com.example.QuanLyBanDienThoai.entity.Chitietdonhang;
import com.example.QuanLyBanDienThoai.entity.Donhang;
import com.example.QuanLyBanDienThoai.service.ChiTietDonHangService;
import com.example.QuanLyBanDienThoai.service.DonHangService;
import com.example.QuanLyBanDienThoai.service.KhachHangService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DonHangController {
    @Autowired
    DonHangService donHangService;
    @Autowired
    KhachHangService khachHangService;
    @Autowired
    ChiTietDonHangService chiTietDonHangService;
    @GetMapping("/checkout")
    public String checkout() {
        return "pages/checkout";
    }

    @GetMapping("/order")
    public String getOrdersBySessionEmail(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return "redirect:/login";
        }

        List<Donhang> orders = donHangService.getDonHangByMaKh(
                khachHangService.findByEmail(email).get().getMakh());
        model.addAttribute("orders", orders);

        Map<Integer, List<Chitietdonhang>> detailList = new HashMap<>();
        for (Donhang order : orders) {
            List<Chitietdonhang> orderDetails = chiTietDonHangService.getChiTietDonHangByMaDh(order.getMadh());
            detailList.put(order.getMadh(), orderDetails);
        }

        model.addAttribute("detailList", detailList);
        return "pages/order";
    }

    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam("madh") int madh, RedirectAttributes redirectAttributes) {
        boolean success = donHangService.cancelOrder(madh);
        if (success) {
            redirectAttributes.addFlashAttribute("success", "Đã huỷ đơn hàng " + madh);
        } else {
            redirectAttributes.addFlashAttribute("error", "Không thể huỷ đơn hàng " + madh);
        }
        return "redirect:/order";
    }

}
