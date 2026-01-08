package com.thegooddeveloper.portfolio.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CertificateDTO {
  private Long certificateId;
  private String categoryName;
  private String verificationLink;
  private String isMajor;
  private String imageLink;
  // private String imageBase64;
  private Long categoryId;

}
