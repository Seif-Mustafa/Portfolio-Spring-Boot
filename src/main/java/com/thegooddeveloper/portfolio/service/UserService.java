package com.thegooddeveloper.portfolio.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thegooddeveloper.mapper.ProjectMapper;
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
import com.thegooddeveloper.portfolio.entity.ProjectImage;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.repository.CategoryRepository;
import com.thegooddeveloper.portfolio.repository.CertificateRepository;
import com.thegooddeveloper.portfolio.repository.ExperienceRepository;
import com.thegooddeveloper.portfolio.repository.ProjectImageRepository;
import com.thegooddeveloper.portfolio.repository.ProjectRepository;
import com.thegooddeveloper.portfolio.repository.UserInfoRepository;

@Service
public class UserService {

  private final UserInfoRepository userInfoRepository;
  private final CertificateRepository certificateRepository;
  private final ProjectRepository projectRepository;
  private final CategoryRepository categoryRepository;
  private final ProjectImageRepository projectImageRepository;
  private final ExperienceRepository experienceRepository;

  @Autowired
  public UserService(
      UserInfoRepository userInfoRepository,
      CertificateRepository certificateRepository,
      ProjectRepository projectRepository,
      CategoryRepository categoryRepository,
      ProjectImageRepository projectImageRepository,
      ExperienceRepository experienceRepository) {
    this.userInfoRepository = userInfoRepository;
    this.certificateRepository = certificateRepository;
    this.projectRepository = projectRepository;
    this.categoryRepository = categoryRepository;
    this.projectImageRepository = projectImageRepository;
    this.experienceRepository = experienceRepository;
  }

  // GET Start

  public UserInfoDTO getUserInfo(Long userId) {
    UserInfo user = userInfoRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

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
    dto.setUserImageBase64(Base64.getEncoder().encodeToString(user.getUserImage()));

    return dto;
  }

  public List<CategoryDTO> getUserCategories(Long userId) {
    if (!userInfoRepository.existsById(userId)) {
      throw new RuntimeException("User not found with ID: " + userId);
    }

    return categoryRepository.findByUserId(userId).stream()
        .map(category -> new CategoryDTO(category.getCategoryId(), category.getCategoryName()))
        .collect(Collectors.toList());
  }

  // Get End
  public UserInfo createUser(CreateUserRequest request) throws IOException {
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
    if (request.getUserImage() != null && !request.getUserImage().isEmpty()) {
      user.setUserImage(request.getUserImage().getBytes());
    } else {
      throw new IllegalArgumentException("User image is required");
    }

    return userInfoRepository.save(user);
  }

  public Category createCategory(CreateCategoryRequest request) {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = new Category();
    category.setUserInfo(user);
    category.setCategoryName(request.getCategoryName());

    return categoryRepository.save(category);
  }

  public Certificate createCertificate(CreateCertificateRequest request) throws IOException {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category not found"));

    Certificate cert = new Certificate();
    cert.setUserInfo(user);
    cert.setCertificateCategory(category);
    cert.setVerificationLink(request.getVerificationLink());
    cert.setIsMajor(request.getIsMajor());

    if (request.getImage() != null && !request.getImage().isEmpty()) {
      cert.setImage(request.getImage().getBytes());
    } else {
      throw new IllegalArgumentException("Certificate image is required");
    }

    return certificateRepository.save(cert);
  }

  public List<CertificateDTO> getMajorCertificates(Long userId) {
    return certificateRepository.findMajorByUserId(userId).stream()
        .map(this::toCertificateDTO)
        .collect(Collectors.toList());
  }

  public List<CertificateDTO> getAllCertificates(Long userId) {
    return certificateRepository.findByUserInfoUserId(userId).stream()
        .map(this::toCertificateDTO)
        .collect(Collectors.toList());
  }

  public List<ProjectDTO> getAllProjects(Long userId) {
    return projectRepository.findByUserInfoUserId(userId).stream()
        .map(ProjectMapper::toDto)
        .collect(Collectors.toList());
  }

  public List<CertificateDTO> getCertificatesByUserAndCategory(Long userId, Long categoryId) {
    return certificateRepository.findByUserIdAndCategoryId(userId, categoryId).stream()
        .map(this::toCertificateDTO).collect(Collectors.toList());
  }

  public List<CategoryDTO> getCategoriesWithCertificates(Long userId) {
    return categoryRepository.findCategoriesWithCertificates(userId).stream()
        .map(c -> new CategoryDTO(c.getCategoryId(), c.getCategoryName()))
        .collect(Collectors.toList());
  }

  public Project createProject(CreateProjectRequest request) throws IOException {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category not found"));

    Project project = new Project();
    project.setUserInfo(user);
    project.setProjectCategory(category);
    project.setProjectPriority(request.getProjectPriority());
    project.setProjectName(request.getProjectName());
    project.setProjectDescription(request.getProjectDescription());
    project.setDevelopmentDate(request.getDevelopmentDate());
    project.setGithubLink(request.getGithubLink());
    project.setPlaystoreLink(request.getPlaystoreLink());
    project.setDeploymentLink(request.getDeploymentLink());
    project.setVideoLink(request.getVideoLink());
    Project savedProject = projectRepository.save(project);

    // Save images
    if (request.getNewImages() != null) {
      for (MultipartFile file : request.getNewImages()) {
        if (!file.isEmpty()) {
          ProjectImage image = new ProjectImage();
          image.setProject(savedProject);
          image.setImage(file.getBytes());
          projectImageRepository.save(image);
        }
      }
    }

    return savedProject;
  }

  public List<ProjectDTO> getProjectByUserAndCategory(Long userId, Long categoryId) {
    return projectRepository.findByUserIdAndCategoryId(userId, categoryId).stream()
        .map(ProjectMapper::toDto)
        .collect(Collectors.toList());
  }

  public List<CategoryDTO> getCategoriesWithProjects(Long userId) {
    return categoryRepository.findCategoriesWithProjects(userId).stream()
        .map(c -> new CategoryDTO(c.getCategoryId(), c.getCategoryName()))
        .collect(Collectors.toList());
  }

  public Experience createExperience(CreateExperienceRequest request) {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Experience exp = new Experience();
    exp.setUserInfo(user);
    exp.setTitle(request.getTitle());
    exp.setDateFrom(request.getDateFrom());
    exp.setDateTo(request.getDateTo());
    exp.setExperianceDetail(request.getExperianceDetail());

    return experienceRepository.save(exp);
  }

  ////////// Update Start

  public UserInfo updateUser(Long userId, UpdateUserRequest request) throws IOException {
    UserInfo user = userInfoRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

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
    if (request.getUserImage() != null && !request.getUserImage().isEmpty()) {
      user.setUserImage(request.getUserImage().getBytes());
    }

    return userInfoRepository.save(user);
  }

  public Category updateCategory(Long categoryId, UpdateCategoryRequest request) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found"));

    if (request.getCategoryName() != null) {
      category.setCategoryName(request.getCategoryName());
    }

    return categoryRepository.save(category);
  }

  public Certificate updateCertificate(Long certificateId, UpdateCertificateRequest request) throws IOException {
    Certificate cert = certificateRepository.findById(certificateId)
        .orElseThrow(() -> new RuntimeException("Certificate not found"));

    if (request.getCategoryId() != null) {
      Category category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found"));
      cert.setCertificateCategory(category);
    }

    if (request.getVerificationLink() != null) {
      cert.setVerificationLink(request.getVerificationLink());
    }

    if (request.getIsMajor() != null) {
      cert.setIsMajor(request.getIsMajor());
    }

    if (request.getImage() != null && !request.getImage().isEmpty()) {
      cert.setImage(request.getImage().getBytes());
    }

    return certificateRepository.save(cert);
  }

  public Project updateProject(Long projectId, UpdateProjectRequest request) throws IOException {
    Project project = projectRepository.findById(projectId)
        .orElseThrow(() -> new RuntimeException("Project not found"));

    if (request.getCategoryId() != null) {
      Category category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found"));
      project.setProjectCategory(category);
    }

    if (request.getProjectName() != null)
      project.setProjectName(request.getProjectName());
    if (request.getProjectPriority() != null)
      project.setProjectPriority(request.getProjectPriority());
    if (request.getProjectDescription() != null)
      project.setProjectDescription(request.getProjectDescription());
    if (request.getDevelopmentDate() != null)
      project.setDevelopmentDate(request.getDevelopmentDate());
    if (request.getGithubLink() != null)
      project.setGithubLink(request.getGithubLink());
    if (request.getPlaystoreLink() != null)
      project.setPlaystoreLink(request.getPlaystoreLink());
    if (request.getDeploymentLink() != null)
      project.setDeploymentLink(request.getDeploymentLink());
    if (request.getVideoLink() != null)
      project.setVideoLink(request.getVideoLink());
    Project savedProject = projectRepository.save(project);

    // Remove all existing images if requested
    if (Boolean.TRUE.equals(request.getReplaceAllImages())) {
      // iterate current images and delete only those that belong to this project
      if (savedProject.getImages() != null) {
        for (ProjectImage img : savedProject.getImages()) {
          // extra safety: ensure association
          if (img.getProject() != null && projectId.equals(img.getProject().getProjectId())) {
            projectImageRepository.deleteById(img.getProjectImageId());
          }
        }
      }
    }

    // Remove specific image ids if provided
    if (request.getRemoveImageIds() != null) {
      for (Long imageId : request.getRemoveImageIds()) {
        projectImageRepository.findById(imageId).ifPresent(img -> {
          if (img.getProject() != null && projectId.equals(img.getProject().getProjectId())) {
            projectImageRepository.deleteById(imageId);
          } else {
            // ignore images that do not belong to this project (or optionally throw)
          }
        });
      }
    }

    // Add new images if any
    if (request.getNewImages() != null) {
      for (MultipartFile file : request.getNewImages()) {
        if (!file.isEmpty()) {
          ProjectImage image = new ProjectImage();
          image.setProject(savedProject);
          image.setImage(file.getBytes());
          projectImageRepository.save(image);
        }
      }
    }

    return savedProject;
  }

  public Experience updateExperience(Long experienceId, UpdateExperienceRequest request) {
    Experience exp = experienceRepository.findById(experienceId)
        .orElseThrow(() -> new RuntimeException("Experience not found"));

    if (request.getTitle() != null)
      exp.setTitle(request.getTitle());
    if (request.getDateFrom() != null)
      exp.setDateFrom(request.getDateFrom());
    if (request.getDateTo() != null)
      exp.setDateTo(request.getDateTo());
    if (request.getExperianceDetail() != null)
      exp.setExperianceDetail(request.getExperianceDetail());

    return experienceRepository.save(exp);
  }

  ///////// Update End

  //////// Delete Start
  public void deleteUser(Long userId) {
    if (!userInfoRepository.existsById(userId)) {
      throw new RuntimeException("User not found");
    }
    userInfoRepository.deleteById(userId);
  }

  public void deleteCategory(Long categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new RuntimeException("Category not found");
    }
    categoryRepository.deleteById(categoryId);
  }

  public void deleteCertificate(Long certificateId) {
    if (!certificateRepository.existsById(certificateId)) {
      throw new RuntimeException("Certificate not found");
    }
    certificateRepository.deleteById(certificateId);
  }

  public void deleteProject(Long projectId) {
    if (!projectRepository.existsById(projectId)) {
      throw new RuntimeException("Project not found");
    }
    projectRepository.deleteById(projectId);
    // ProjectImage rows are automatically deleted if you have CascadeType.ALL
  }

  public void deleteProjectImage(Long imageId) {
    if (!projectImageRepository.existsById(imageId)) {
      throw new RuntimeException("Project image not found");
    }
    projectImageRepository.deleteById(imageId);
  }

  public void deleteExperience(Long experienceId) {
    if (!experienceRepository.existsById(experienceId)) {
      throw new RuntimeException("Experience not found");
    }
    experienceRepository.deleteById(experienceId);
  }

  //////// Delete End

  private CertificateDTO toCertificateDTO(Certificate cert) {
    CertificateDTO dto = new CertificateDTO();
    dto.setCertificateId(cert.getCertificateId());
    dto.setCategoryId(cert.getCertificateCategory().getCategoryId());

    dto.setCategoryName(cert.getCertificateCategory().getCategoryName());
    dto.setVerificationLink(cert.getVerificationLink());
    dto.setIsMajor(cert.getIsMajor());
    dto.setImageBase64(Base64.getEncoder().encodeToString(cert.getImage()));

    return dto;
  }

}
