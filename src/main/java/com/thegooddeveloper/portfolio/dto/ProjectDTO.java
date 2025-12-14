package com.thegooddeveloper.portfolio.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjectDTO {
  private Long projectId;
  private String projectName;
  private Integer projectPriority;
  private String projectDescription;
  private LocalDate developmentDate;
  private String githubLink;
  private String playstoreLink;
  private String deploymentLink;
    private String videoLink;
  private Long categoryId;
  private String categoryName;
   // New: structured images with ids + base64
    private List<ProjectImageDTO> images = new ArrayList<>();

 }
