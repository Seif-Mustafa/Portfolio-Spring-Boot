package com.thegooddeveloper.portfolio.service.impl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.thegooddeveloper.portfolio.dto.UserInfoDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateUserRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateUserRequest;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.mapper.UserMapper;
import com.thegooddeveloper.portfolio.repository.UserInfoRepository;
import com.thegooddeveloper.portfolio.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserInfoRepository userInfoRepository;
  private final UserMapper userMapper;

  @Override
  public UserInfoDTO getUserInfo(Long userId) {
    UserInfo user = userInfoRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return userMapper.toDto(user);
  }

  @Override
  public UserInfo createUser(CreateUserRequest request) throws IOException {
    return userInfoRepository.save(userMapper.toEntity(request));
  }

  @Override
  public UserInfo updateUser(Long userId, UpdateUserRequest request) throws IOException {
    UserInfo user = userInfoRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    return userInfoRepository.save(userMapper.toEntity(user, request));
  }

  @Override
  public void deleteUser(Long userId) {
    if (!userInfoRepository.existsById(userId)) {
      throw new RuntimeException("User not found");
    }
    userInfoRepository.deleteById(userId);
  }

  // public List<CategoryDTO> getUserCategories(Long userId) {
  // if (!userInfoRepository.existsById(userId)) {
  // throw new RuntimeException("User not found with ID: " + userId);
  // }

  // return categoryRepository.findByUserId(userId).stream()
  // .map(category -> new CategoryDTO(category.getCategoryId(),
  // category.getIsHidden(),category.getCategoryName()))
  // .collect(Collectors.toList());
  // }

  // Get End

  // public Category createCategory(CreateCategoryRequest request) {
  // UserInfo user = userInfoRepository.findById(request.getUserId())
  // .orElseThrow(() -> new RuntimeException("User not found"));

  // Category category = new Category();
  // category.setUserInfo(user);
  // category.setCategoryName(request.getCategoryName());

  // return categoryRepository.save(category);
  // }

  // public List<CertificateDTO> getMajorCertificates(Long userId) {
  // return certificateRepository.findMajorByUserId(userId).stream()
  // .map(this::toCertificateDTO)
  // .collect(Collectors.toList());
  // }

  // public List<CertificateDTO> getAllCertificates(Long userId) {
  // return certificateRepository.findByUserInfoUserId(userId).stream()
  // .map(this::toCertificateDTO)
  // .collect(Collectors.toList());
  // }

  // public List<ProjectDTO> getAllProjects(Long userId) {
  // return projectRepository.findByUserInfoUserId(userId).stream()
  // .map(projectMapper::toDto)
  // .collect(Collectors.toList());
  // }

  // public List<CertificateDTO> getCertificatesByUserAndCategory(Long userId,
  // Long categoryId) {
  // return certificateRepository.findByUserIdAndCategoryId(userId,
  // categoryId).stream()
  // .map(this::toCertificateDTO).collect(Collectors.toList());
  // }

  // public List<CategoryDTO> getCategoriesWithCertificates(Long userId) {
  // return categoryRepository.findCategoriesWithCertificates(userId).stream()
  // .map(c -> new CategoryDTO(c.getCategoryId(),c.getIsHidden(),
  // c.getCategoryName()))
  // .collect(Collectors.toList());
  // }

  // public List<ProjectDTO> getProjectByUserAndCategory(Long userId, Long
  // categoryId) {
  // return projectRepository.findByUserIdAndCategoryId(userId,
  // categoryId).stream()
  // .map(projectMapper::toDto)
  // .collect(Collectors.toList());
  // }

  // public List<CategoryDTO> getCategoriesWithProjects(Long userId) {
  // return categoryRepository.findCategoriesWithProjects(userId).stream()
  // .map(c -> new CategoryDTO(c.getCategoryId(), c.getIsHidden(),
  // c.getCategoryName()))
  // .collect(Collectors.toList());
  // }

  ////////// Update Start

  // public Experience updateExperience(Long experienceId, UpdateExperienceRequest
  // request) {
  // Experience exp = experienceRepository.findById(experienceId)
  // .orElseThrow(() -> new RuntimeException("Experience not found"));

  // if (request.getTitle() != null)
  // exp.setTitle(request.getTitle());
  // if (request.getDateFrom() != null)
  // exp.setDateFrom(request.getDateFrom());
  // if (request.getDateTo() != null)
  // exp.setDateTo(request.getDateTo());
  // if (request.getExperianceDetail() != null)
  // exp.setExperianceDetail(request.getExperianceDetail());

  // return experienceRepository.save(exp);
  // }

  ///////// Update End

  //////// Delete Start

  // public void deleteExperience(Long experienceId) {
  // if (!experienceRepository.existsById(experienceId)) {
  // throw new RuntimeException("Experience not found");
  // }
  // experienceRepository.deleteById(experienceId);
  // }

  //////// Delete End

}
