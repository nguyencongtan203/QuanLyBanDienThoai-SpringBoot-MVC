package com.example.QuanLyBanDienThoai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthConfig {
    private final EnvConfig envConfig;

    public OAuthConfig(EnvConfig envConfig) {
        this.envConfig = envConfig;
    }

    @Bean
    public String googleClientId() {
        return envConfig.getProperty("GOOGLE_CLIENT_ID");
    }

    @Bean
    public String googleClientSecret() {
        return envConfig.getProperty("GOOGLE_CLIENT_SECRET");
    }
}
