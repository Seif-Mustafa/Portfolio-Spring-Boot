package com.thegooddeveloper.portfolio.service.impl;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateProjectRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateProjectRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.entity.ProjectImage;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.mapper.ProjectImageMapper;
import com.thegooddeveloper.portfolio.mapper.ProjectMapper;
import com.thegooddeveloper.portfolio.repository.*;
import com.thegooddeveloper.portfolio.service.ProjectService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

  private final ProjectRepository projectRepository;
  private final CategoryRepository categoryRepository;
  private final ProjectImageRepository projectImageRepository;
  private final UserInfoRepository userInfoRepository;
  private final ProjectMapper projectMapper;
  private final ProjectImageMapper projectImageMapper;

  public List<ProjectDTO> getAllProjectsByOptionalFilters(Long userId, Long categoryId, Long projectId,
      String isHidden) {
    List<Project> filteredProjects = projectRepository.getAllProjectsByOptionalFilters(userId, categoryId, projectId,
        isHidden);

    return filteredProjects.stream().map(projectMapper::toDto).collect(Collectors.toList());
  }

  @Transactional
  public int changeProjectVisibility(Long userId, Long categoryId, Long projectId, String isHidden) {
    int updatedRecordsCount = projectRepository.changeProjectVisibility(userId, categoryId, projectId, isHidden);

    return updatedRecordsCount;
  }

  public Project createProject(CreateProjectRequest request) throws IOException {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category not found"));

    Project project = projectMapper.toEntity(request, user, category);
    Project savedProject = projectRepository.save(project);

    if (request.getImagesLinks() != null) {
      List<ProjectImage> imagesEntities = projectImageMapper.toListOfEntities(savedProject, request.getImagesLinks());
      savedProject.setImages(
          projectImageRepository.saveAll(imagesEntities));

    }

    // Save images
    // if (request.getImagesLinks() != null) {
    // for (String imageLink : request.getImagesLinks()) {
    // if (!imageLink.isEmpty()) {
    // ProjectImage image = new ProjectImage();
    // image.setProject(savedProject);
    // // image.setImage(file.getBytes());
    // image.setImageLink(imageLink);
    // projectImageRepository.save(image);
    // }
    // }
    // }
    return savedProject;
  }

  @Override
  @Transactional
  public ProjectDTO updateProject(Long projectId, UpdateProjectRequest request) throws IOException {
  
      Project project = projectRepository.findById(projectId)
              .orElseThrow(() -> new RuntimeException("Project not found"));
  
      /* =========================
         Update basic fields
         ========================= */
  
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
  
      /* =========================
         IMAGE HANDLING
         ========================= */
  
      // 1️⃣ Replace all images
      if (Boolean.TRUE.equals(request.getReplaceAllImages())) {
  
          // Delete from DB
          List<ProjectImage> existingImages =
                  projectImageRepository.findByProject_ProjectId(projectId);
          projectImageRepository.deleteAll(existingImages);
  
          // Clear entity collection (VERY IMPORTANT)
          project.getImages().clear();
      }
  
      // 2️⃣ Remove specific image IDs
      if (request.getRemoveImageIds() != null && !request.getRemoveImageIds().isEmpty()) {
  
          for (Long imageId : request.getRemoveImageIds()) {
              projectImageRepository.findById(imageId).ifPresent(img -> {
  
                  if (img.getProject() != null &&
                          projectId.equals(img.getProject().getProjectId())) {
  
                      projectImageRepository.delete(img);
                      project.getImages().remove(img); // keep entity in sync
                  }
              });
          }
      }
  
      // 3️⃣ Add new images via links
      if (request.getImagesLinks() != null && !request.getImagesLinks().isEmpty()) {
  
          for (String imageLink : request.getImagesLinks()) {
  
              if (imageLink != null && !imageLink.isBlank()) {
  
                  ProjectImage image = new ProjectImage();
                  image.setProject(project);
                  image.setImageLink(imageLink);
  
                  projectImageRepository.save(image);
                  project.getImages().add(image); // keep entity in sync
              }
          }
      }
  
      /* =========================
         Persist & refresh
         ========================= */
  
      projectRepository.save(project);
      projectRepository.flush();
  
      Project refreshedProject = projectRepository.findById(projectId)
              .orElseThrow(() -> new RuntimeException("Project not found after update"));
  
      return projectMapper.toDto(refreshedProject);
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
}
