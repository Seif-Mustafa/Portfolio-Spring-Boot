package com.thegooddeveloper.portfolio.service;

import java.io.IOException;

import com.thegooddeveloper.portfolio.dto.UserInfoDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateUserRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateUserRequest;
import com.thegooddeveloper.portfolio.entity.UserInfo;

public interface UserService {
  UserInfoDTO getUserInfo(Long userId);
  UserInfo createUser(CreateUserRequest request) throws IOException;
  UserInfo updateUser(Long userId, UpdateUserRequest request) throws IOException ;
  void deleteUser(Long userId);
}
