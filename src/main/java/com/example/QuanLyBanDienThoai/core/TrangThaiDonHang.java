package com.example.QuanLyBanDienThoai.core;

public enum TrangThaiDonHang {
    DELINQUENT("Đang giao"),
    HANDLING("Đang xử lý"),
    CANCELLED("Đã huỷ"),
    REFUND("Hoàn đơn hàng"),
    COMPLETED("Hoàn tất");

    private final String value;

    TrangThaiDonHang(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TrangThaiDonHang fromString(String value) {
        for (TrangThaiDonHang flightStatus : TrangThaiDonHang.values()) {
            if (flightStatus.value.equalsIgnoreCase(value)) {
                return flightStatus;
            }
        }
        throw new IllegalArgumentException("Unknown: " + value);
    }
}
