package com.thegooddeveloper.portfolio.service;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.request.UpdateProjectRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.entity.ProjectImage;
import com.thegooddeveloper.portfolio.mapper.ProjectMapper;
import com.thegooddeveloper.portfolio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final ProjectImageRepository projectImageRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(
            ProjectRepository projectRepository,
            CategoryRepository categoryRepository,
            ProjectImageRepository projectImageRepository,
            ProjectMapper projectMapper) {

        this.projectRepository = projectRepository;
        this.categoryRepository = categoryRepository;
        this.projectImageRepository = projectImageRepository;
        this.projectMapper = projectMapper;
    }


    public List<ProjectDTO> getAllProjectsByOptionalFilters(Long userId, Long categoryId, String isHidden) {
        List<Project> filteredProjects = projectRepository.getAllProjectsByOptionalFilters(userId, categoryId, isHidden);

        return filteredProjects.stream().map(ProjectMapper::toDto).collect(Collectors.toList());
    }


    @Transactional
    public int changeProjectVisibility(Long userId, Long categoryId, Long projectId, String isHidden) {
        int updatedRecordsCount = projectRepository.changeProjectVisibility(userId, categoryId, projectId, isHidden);

        return updatedRecordsCount;
    }

    public ProjectDTO updateProject(Long projectId, UpdateProjectRequest request) throws IOException {
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

        return ProjectMapper.toDto(savedProject);
    }

}
