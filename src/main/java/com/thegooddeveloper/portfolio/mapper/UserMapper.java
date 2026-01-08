package com.thegooddeveloper.portfolio.mapper;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.thegooddeveloper.portfolio.dto.UserInfoDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateUserRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateUserRequest;
import com.thegooddeveloper.portfolio.entity.UserInfo;

@Component
public class UserMapper {

  public UserInfoDTO toDto(UserInfo user) {
    UserInfoDTO dto = new UserInfoDTO();
    dto.setUserId(user.getUserId());
    dto.setUsername(user.getUsername());
    dto.setJobTitle(user.getJobTitle());
    dto.setSummary(user.getSummary());
    dto.setPhoneNumber(user.getPhoneNumber());
    dto.setEmail(user.getEmail());
    dto.setWhatsappLink(user.getWhatsappLink());
    dto.setLinkedinLink(user.getLinkedinLink());
    dto.setGithubLink(user.getGithubLink());
    dto.setImageLink(user.getImageLink());
    // dto.setUserImageBase64(Base64.getEncoder().encodeToString(user.getUserImage()));
    return dto;
  }

  public UserInfo toEntity(CreateUserRequest request) throws IOException {
    UserInfo user = new UserInfo();
    user.setUsername(request.getUsername());
    user.setJobTitle(request.getJobTitle());
    user.setSummary(request.getSummary());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setEmail(request.getEmail());
    user.setWhatsappLink(request.getWhatsappLink());
    user.setLinkedinLink(request.getLinkedinLink());
    user.setGithubLink(request.getGithubLink());
    // Handle image
    if (request.getImageLink() != null && !request.getImageLink().isEmpty()) {
      user.setImageLink(request.getImageLink());
    } else {
      throw new IllegalArgumentException("User image is required");
    }

    // if (request.getUserImage() != null && !request.getUserImage().isEmpty()) {
    // user.setUserImage(request.getUserImage().getBytes());
    // } else {
    // throw new IllegalArgumentException("User image is required");
    // }

    return user;
  }

  public UserInfo toEntity(UserInfo user, UpdateUserRequest request) throws IOException {
    // Update fields
    if (request.getUsername() != null)
      user.setUsername(request.getUsername());
    if (request.getJobTitle() != null)
      user.setJobTitle(request.getJobTitle());
    if (request.getSummary() != null)
      user.setSummary(request.getSummary());
    if (request.getPhoneNumber() != null)
      user.setPhoneNumber(request.getPhoneNumber());
    if (request.getEmail() != null)
      user.setEmail(request.getEmail());
    if (request.getWhatsappLink() != null)
      user.setWhatsappLink(request.getWhatsappLink());
    if (request.getLinkedinLink() != null)
      user.setLinkedinLink(request.getLinkedinLink());
    if (request.getGithubLink() != null)
      user.setGithubLink(request.getGithubLink());

    // Update image if provided
    if (request.getImageLink() != null && !request.getImageLink().isEmpty()) {
      user.setImageLink(request.getImageLink());
    }
    // if (request.getUserImage() != null && !request.getUserImage().isEmpty()) {
    // user.setUserImage(request.getUserImage().getBytes());
    // }

    return user;
  }

}
