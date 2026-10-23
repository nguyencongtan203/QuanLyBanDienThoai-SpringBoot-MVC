package com.example.QuanLyBanDienThoai.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class EnvConfig {
    private Properties envProperties = new Properties();

    @PostConstruct
    public void init() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Bỏ qua comment và dòng trống
                if (line.trim().startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    // Xóa quotes nếu có
                    value = value.replaceAll("^\"|\"$", "");
                    value = value.replaceAll("^'|'$", "");

                    System.setProperty(key, value);
                    envProperties.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            System.err.println("Could not load .env file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return envProperties.getProperty(key);
    }
}
