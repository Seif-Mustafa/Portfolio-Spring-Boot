package com.thegooddeveloper.portfolio.dto;

import lombok.Data;

@Data
public class UserInfoDTO {
  private Long userId;
  private String username;
  private String jobTitle;
  private String summary;
  private String phoneNumber;
  private String email;
  private String whatsappLink;
  private String linkedinLink;
  private String githubLink;
  private String imageLink;

  // private String userImageBase64;

}
