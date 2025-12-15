package com.thegooddeveloper.portfolio.mapper;



import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.ProjectImageDTO;
import com.thegooddeveloper.portfolio.entity.Project;

public class ProjectMapper {

  public static ProjectDTO toDto(Project project) {
    if (project == null) return null;
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
        pid.setImageBase64(Base64.getEncoder().encodeToString(img.getImage()));
        return pid;
      }).collect(Collectors.toList());
      dto.setImages(imgs);
    }
    return dto;
  }

  public static List<ProjectDTO> toDtoList(List<Project> projects) {
    return projects == null ? List.of() : projects.stream().map(ProjectMapper::toDto).collect(Collectors.toList());
  }
}
