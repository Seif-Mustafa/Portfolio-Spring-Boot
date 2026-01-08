package com.thegooddeveloper.portfolio.dto.request;


import lombok.Data;

@Data
public class CreateUserRequest {
    private String username;
    // private MultipartFile userImage; // uploaded file
    private String jobTitle;
    private String summary;
    private String phoneNumber;
    private String email;
    private String whatsappLink;
    private String linkedinLink;
    private String githubLink;
    private String imageLink;

}