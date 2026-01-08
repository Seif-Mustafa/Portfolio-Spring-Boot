package com.thegooddeveloper.portfolio.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.entity.ProjectImage;

@Component
public class ProjectImageMapper {

  public ProjectImage toEntity(Project project, String imageLink) {
    ProjectImage image = new ProjectImage();
    image.setProject(project);
    // image.setImage(file.getBytes());
    image.setImageLink(imageLink);
    return image;
  }

  public List<ProjectImage> toListOfEntities(Project project, List<String> imagesLinks) {
    return imagesLinks.stream().map((imageLink) -> toEntity(project, imageLink)).collect(Collectors.toList());
  }
}
