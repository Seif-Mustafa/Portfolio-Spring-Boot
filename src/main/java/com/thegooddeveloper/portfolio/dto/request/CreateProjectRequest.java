package com.thegooddeveloper.portfolio.dto.request;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private Long userId;
    private Long categoryId;
    private String projectName;
    private Integer projectPriority;
    private String projectDescription;
    private LocalDate developmentDate;
    private String githubLink;
    private String playstoreLink;
    private String deploymentLink;
      private String videoLink;
    private List<MultipartFile> newImages; // multiple images
}