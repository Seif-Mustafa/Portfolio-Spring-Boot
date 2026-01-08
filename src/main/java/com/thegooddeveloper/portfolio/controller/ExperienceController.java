package com.thegooddeveloper.portfolio.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import com.thegooddeveloper.portfolio.dto.request.CreateExperienceRequest;
import com.thegooddeveloper.portfolio.entity.Experience;
import com.thegooddeveloper.portfolio.service.ExperienceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/experiences")
@RequiredArgsConstructor
public class ExperienceController {
  private ExperienceService experienceService;

  @PostMapping("/experiences")
  public ApiResponse<Experience> createExperience(
      @RequestBody CreateExperienceRequest request) {
    return ApiResponse.created(experienceService.createExperience(request), "Experiance Created Successfully");
  }

  // // Update Start

  // @PutMapping("/experiences/{experienceId}")
  // public ResponseEntity<Experience> updateExperience(
  // @PathVariable Long experienceId,
  // @RequestBody UpdateExperienceRequest request) {
  // Experience updated = userService.updateExperience(experienceId, request);
  // return ResponseEntity.ok(updated);
  // }

  // // Update End

  // // Delete Start

  // @DeleteMapping("/experiences/{experienceId}")
  // public ResponseEntity<Void> deleteExperience(@PathVariable Long experienceId)
  // {
  // userService.deleteExperience(experienceId);
  // return ResponseEntity.noContent().build();
  // }

  // Delete End
}
