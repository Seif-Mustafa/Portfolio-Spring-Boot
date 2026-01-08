package com.thegooddeveloper.portfolio.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.ProjectImageDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateProjectRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.entity.UserInfo;

import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

  public Project toEntity(CreateProjectRequest request, UserInfo user, Category category) {
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
    project.setIsHidden("N");

    return project;
  }

  public ProjectDTO toDto(Project project) {
    if (project == null)
      return null;
    ProjectDTO dto = new ProjectDTO();
    dto.setProjectId(project.getProjectId());
    dto.setProjectName(project.getProjectName());
    dto.setProjectPriority(project.getProjectPriority());
    dto.setProjectDescription(project.getProjectDescription());
    dto.setDevelopmentDate(project.getDevelopmentDate());
    dto.setGithubLink(project.getGithubLink());
    dto.setPlaystoreLink(project.getPlaystoreLink());
    dto.setDeploymentLink(project.getDeploymentLink());
    dto.setVideoLink(project.getVideoLink());
    dto.setIsHidden(project.getIsHidden());
    if (project.getProjectCategory() != null) {
      dto.setCategoryId(project.getProjectCategory().getCategoryId());
      dto.setCategoryName(project.getProjectCategory().getCategoryName());
    }

    if (project.getImages() != null) {
      List<ProjectImageDTO> imgs = project.getImages().stream().map(img -> {
        ProjectImageDTO pid = new ProjectImageDTO();
        pid.setProjectImageId(img.getProjectImageId());
        pid.setImageLink(img.getImageLink());
        // pid.setImageBase64(Base64.getEncoder().encodeToString(img.getImage()));
        return pid;
      }).collect(Collectors.toList());
      dto.setImages(imgs);
    }
    return dto;
  }

  public List<ProjectDTO> toDtoList(List<Project> projects) {
    return projects == null
        ? List.of()
        : projects.stream().map(this::toDto).collect(Collectors.toList());
  }
}
