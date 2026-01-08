package com.thegooddeveloper.portfolio.dto.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCertificateRequest {
    private Long userId;
    private Long categoryId;
    private String imageLink;
    // private MultipartFile image; // Optional
    private String verificationLink;
    private String isMajor;
}
