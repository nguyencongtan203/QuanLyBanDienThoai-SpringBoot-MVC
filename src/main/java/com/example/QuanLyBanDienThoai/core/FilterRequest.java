package com.example.QuanLyBanDienThoai.core;

import java.util.List;

public class FilterRequest {
    private List<String> rams;
    private List<String> storages;
    private List<Integer> brands;
    public FilterRequest() {
    }

    public FilterRequest(List<String> rams, List<String> storages, List<Integer> brands) {
        this.rams = rams;
        this.storages = storages;
        this.brands = brands;
    }

    public List<String> getRams() {
        return rams;
    }

    public void setRams(List<String> rams) {
        this.rams = rams;
    }

    public List<String> getStorages() {
        return storages;
    }

    public void setStorages(List<String> storages) {
        this.storages = storages;
    }

    public List<Integer> getBrands() {
        return brands;
    }

    public void setBrands(List<Integer> brands) {
        this.brands = brands;
    }
}
