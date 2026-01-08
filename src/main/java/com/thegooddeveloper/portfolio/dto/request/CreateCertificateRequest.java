package com.thegooddeveloper.portfolio.dto.request;


import lombok.Data;

@Data
public class CreateCertificateRequest {
    private Long userId;
    private Long categoryId;
    private String imageLink;
    // private MultipartFile image;
    private String verificationLink;
    private String isMajor; // "Y" or "N"
}