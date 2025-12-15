package com.thegooddeveloper.portfolio.controller;

import com.thegooddeveloper.portfolio.dto.ProjectDTO;
import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import com.thegooddeveloper.portfolio.dto.request.UpdateProjectRequest;
import com.thegooddeveloper.portfolio.entity.Project;
import com.thegooddeveloper.portfolio.mapper.ProjectMapper;
import com.thegooddeveloper.portfolio.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("")
    public ApiResponse<List<ProjectDTO>> getAllProjectsByOptionalFilters(@RequestParam(value = "userId", required = false) Long userId,
                                                                         @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                                         @RequestParam(value = "isHidden", required = false) String isHidden) {
        return ApiResponse.success(projectService.getAllProjectsByOptionalFilters(userId, categoryId, isHidden), "Filtered Projects Returned Successfully");
    }

    @PutMapping("/change-visibility")
    public ApiResponse<Integer> changeProjectVisibility(@RequestParam(value = "userId", required = false) Long userId,
                                                        @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                        @RequestParam(value = "projectId", required = false) Long projectId,
                                                        @RequestParam(value = "isHidden") String isHidden) {
        int updatedRecordsCount = projectService.changeProjectVisibility(userId, categoryId, projectId, isHidden);
        return ApiResponse.success(updatedRecordsCount, String.format("%d Projects Changed Visibility Successfully", updatedRecordsCount));
    }


    @PutMapping("/{projectId}")
    public ApiResponse<ProjectDTO> updateProject(
            @PathVariable Long projectId,
            @ModelAttribute UpdateProjectRequest request) throws IOException {
        return ApiResponse.success(projectService.updateProject(projectId, request), "Project Updated Successfully");
    }

    
}
