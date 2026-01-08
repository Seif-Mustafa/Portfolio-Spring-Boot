package com.thegooddeveloper.portfolio.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.*;

import com.thegooddeveloper.portfolio.dto.UserInfoDTO;
import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import com.thegooddeveloper.portfolio.dto.request.CreateUserRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateUserRequest;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // Get Start
  @GetMapping("/health")
  public String checkHealth() {
    return "Application is running and web server is active!";
  }

  @GetMapping("/{userId}")
  public ApiResponse<UserInfoDTO> getUserInfo(@PathVariable Long userId) {
    return ApiResponse.success(userService.getUserInfo(userId), "User Returned Successfully");
  }

  @PostMapping
  public ApiResponse<UserInfo> createUser(
      @RequestBody CreateUserRequest request) throws IOException {
    return ApiResponse.created(userService.createUser(request), "User Created Successfully");
  }

  @PutMapping("/{userId}")
  public ApiResponse<UserInfo> updateUser(
      @PathVariable Long userId,
      @RequestBody UpdateUserRequest request) throws IOException {
    return ApiResponse.success(userService.updateUser(userId, request), "User Updated Successfully");
  }

  @DeleteMapping("/{userId}")
  public ApiResponse<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ApiResponse.noContent("User Deleted Successfully"); // 204 No Content
  }

}
