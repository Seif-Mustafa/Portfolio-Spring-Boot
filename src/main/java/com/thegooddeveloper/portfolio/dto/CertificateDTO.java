package com.thegooddeveloper.portfolio.dto;

import lombok.Data;

@Data
public class CertificateDTO {
  private Long certificateId;
  private String categoryName;
  private String verificationLink;
  private String isMajor;
  private String imageBase64;
  private Long categoryId;

}
