package com.thegooddeveloper.portfolio.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateCertificateRequest {
    private Long userId;
    private Long categoryId;
    private MultipartFile image; // Optional
    private String verificationLink;
    private String isMajor;
}
