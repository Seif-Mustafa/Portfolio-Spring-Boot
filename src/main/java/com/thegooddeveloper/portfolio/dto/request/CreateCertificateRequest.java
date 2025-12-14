package com.thegooddeveloper.portfolio.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateCertificateRequest {
    private Long userId;
    private Long categoryId;
    private MultipartFile image;
    private String verificationLink;
    private String isMajor; // "Y" or "N"
}