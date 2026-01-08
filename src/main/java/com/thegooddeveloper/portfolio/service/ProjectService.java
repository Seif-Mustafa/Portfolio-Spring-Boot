package com.thegooddeveloper.portfolio.service;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateProjectRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateProjectRequest;
import com.thegooddeveloper.portfolio.entity.Project;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

  List<ProjectDTO> getAllProjectsByOptionalFilters(Long userId, Long categoryId, Long projectId, String isHidden);

  int changeProjectVisibility(Long userId, Long categoryId, Long projectId, String isHidden);

  Project createProject(CreateProjectRequest request) throws IOException;

  ProjectDTO updateProject(Long projectId, UpdateProjectRequest request) throws IOException;

  void deleteProject(Long projectId);

  void deleteProjectImage(Long imageId);

}
