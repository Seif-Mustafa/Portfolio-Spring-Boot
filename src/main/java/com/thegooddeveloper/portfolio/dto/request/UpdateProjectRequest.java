package com.thegooddeveloper.portfolio.dto.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UpdateProjectRequest {
  private Long categoryId;
  private String projectName;
  private Integer projectPriority;
  private String projectDescription;
  private LocalDate developmentDate;
  private String githubLink;
  private String playstoreLink;
  private String deploymentLink;
    private String videoLink;
  // existing field that you already have for adding images:
  private List<MultipartFile> newImages;

  // NEW: IDs of project images to remove
  private List<Long> removeImageIds;

  // NEW: if true, remove all existing images before adding newImages
  private Boolean replaceAllImages;
}
