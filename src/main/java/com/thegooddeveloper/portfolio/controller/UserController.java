package com.thegooddeveloper.portfolio.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.thegooddeveloper.portfolio.mapper.ProjectMapper;
import com.thegooddeveloper.portfolio.dto.CategoryDTO;
import com.thegooddeveloper.portfolio.dto.CertificateDTO;
import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.UserInfoDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCategoryRequest;
import com.thegooddeveloper.portfolio.dto.request.CreateCertificateRequest;
import com.thegooddeveloper.portfolio.dto.request.CreateExperienceRequest;
import com.thegooddeveloper.portfolio.dto.request.CreateProjectRequest;
import com.thegooddeveloper.portfolio.dto.request.CreateUserRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCategoryRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCertificateRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateExperienceRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateProjectRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateUserRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Certificate;
import com.thegooddeveloper.portfolio.entity.Experience;
import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  private UserService userService;

  // Get Start
  @GetMapping("/health")
  public String checkHealth() {
    return "Application is running and web server is active!";
  }

  // GET /api/users/1
  @GetMapping("/users/{userId}")
  public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Long userId) {
    UserInfoDTO userInfo = userService.getUserInfo(userId);
    return ResponseEntity.ok(userInfo);
  }

  @GetMapping("/{userId}/categories")
  public ResponseEntity<List<CategoryDTO>> getUserCategories(@PathVariable Long userId) {
    List<CategoryDTO> categories = userService.getUserCategories(userId);
    return ResponseEntity.ok(categories);
  }

  // GET /api/users/1/certificates/all
  @GetMapping("/users/{userId}/certificates/all")
  public ResponseEntity<List<CertificateDTO>> getAllCertificates(@PathVariable Long userId) {
    List<CertificateDTO> certs = userService.getAllCertificates(userId);
    return ResponseEntity.ok(certs);
  }

  @GetMapping("/users/{userId}/projects/all")
  public ResponseEntity<List<ProjectDTO>> getAllProjects(@PathVariable Long userId) {
    List<ProjectDTO> projects = userService.getAllProjects(userId);
    return ResponseEntity.ok(projects);
  }

  // GET /api/users/1/certificates/category/5
  @GetMapping("/users/{userId}/certificates/category/{categoryId}")
  public ResponseEntity<List<CertificateDTO>> getCertificatesByUserAndCategory(@PathVariable Long userId,
      @PathVariable Long categoryId) {
    List<CertificateDTO> certs = userService.getCertificatesByUserAndCategory(userId, categoryId);
    return ResponseEntity.ok(certs);
  }

  // GET /api/users/1/categories/certificates
  @GetMapping("/users/{userId}/categories/certificates")
  public ResponseEntity<List<CategoryDTO>> getCategoriesWithCertificates(@PathVariable Long userId) {
    List<CategoryDTO> categories = userService.getCategoriesWithCertificates(userId);
    return ResponseEntity.ok(categories);
  }

  // GET /api/users/1/projects/category/5
  @GetMapping("/users/{userId}/projects/category/{categoryId}")
  public ResponseEntity<List<ProjectDTO>> getProjectsByUserAndCategory(@PathVariable Long userId,
      @PathVariable Long categoryId) {
    List<ProjectDTO> projects = userService.getProjectByUserAndCategory(userId, categoryId);
    return ResponseEntity.ok(projects);
  }

  // GET /api/users/1/categories/projects
  @GetMapping("/users/{userId}/categories/projects")
  public ResponseEntity<List<CategoryDTO>> getCategoriesWithProjects(@PathVariable Long userId) {
    List<CategoryDTO> categories = userService.getCategoriesWithProjects(userId);
    return ResponseEntity.ok(categories);
  }

  // Get End

  // Post Start

  @PostMapping("/users")
  public ResponseEntity<UserInfo> createUser(
      @ModelAttribute CreateUserRequest request) throws IOException {
    UserInfo user = userService.createUser(request);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/categories")
  public ResponseEntity<Category> createCategory(
      @RequestBody CreateCategoryRequest request) {
    Category category = userService.createCategory(request);
    return ResponseEntity.ok(category);
  }

  @PostMapping("/certificates")
  public ResponseEntity<Certificate> createCertificate(
      @ModelAttribute CreateCertificateRequest request) throws IOException {
    Certificate cert = userService.createCertificate(request);
    return ResponseEntity.ok(cert);
  }

  @PostMapping("/projects")
  public ResponseEntity<Project> createProject(
      @ModelAttribute CreateProjectRequest request) throws IOException {
    Project project = userService.createProject(request);
    return ResponseEntity.ok(project);
  }

  @PostMapping("/experiences")
  public ResponseEntity<Experience> createExperience(
      @RequestBody CreateExperienceRequest request) {
    Experience exp = userService.createExperience(request);
    return ResponseEntity.ok(exp);
  }

  // Update Start
  @PutMapping("/users/{userId}")
  public ResponseEntity<UserInfo> updateUser(
      @PathVariable Long userId,
      @ModelAttribute UpdateUserRequest request) throws IOException {
    UserInfo updated = userService.updateUser(userId, request);
    return ResponseEntity.ok(updated);
  }

  @PutMapping("/categories/{categoryId}")
  public ResponseEntity<Category> updateCategory(
      @PathVariable Long categoryId,
      @RequestBody UpdateCategoryRequest request) {
    Category updated = userService.updateCategory(categoryId, request);
    return ResponseEntity.ok(updated);
  }

  @PutMapping("/certificates/{certificateId}")
  public ResponseEntity<Certificate> updateCertificate(
      @PathVariable Long certificateId,
      @ModelAttribute UpdateCertificateRequest request) throws IOException {
    Certificate updated = userService.updateCertificate(certificateId, request);
    return ResponseEntity.ok(updated);
  }

  @PutMapping("/projects/{projectId}")
  public ResponseEntity<ProjectDTO> updateProject(
      @PathVariable Long projectId,
      @ModelAttribute UpdateProjectRequest request) throws IOException {
    Project updated = userService.updateProject(projectId, request);
    return ResponseEntity.ok(ProjectMapper.toDto(updated));
  }

  @PutMapping("/experiences/{experienceId}")
  public ResponseEntity<Experience> updateExperience(
      @PathVariable Long experienceId,
      @RequestBody UpdateExperienceRequest request) {
    Experience updated = userService.updateExperience(experienceId, request);
    return ResponseEntity.ok(updated);
  }

  // Update End

  // Delete Start

  @DeleteMapping("/users/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build(); // 204 No Content
  }

  @DeleteMapping("/categories/{categoryId}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId) {
    userService.deleteCategory(categoryId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/certificates/{certificateId}")
  public ResponseEntity<Void> deleteCertificate(@PathVariable Long certificateId) {
    userService.deleteCertificate(certificateId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/projects/{projectId}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
    userService.deleteProject(projectId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/project-images/{imageId}")
  public ResponseEntity<Void> deleteProjectImage(@PathVariable Long imageId) {
    userService.deleteProjectImage(imageId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/experiences/{experienceId}")
  public ResponseEntity<Void> deleteExperience(@PathVariable Long experienceId) {
    userService.deleteExperience(experienceId);
    return ResponseEntity.noContent().build();
  }

  // Delete End

}
