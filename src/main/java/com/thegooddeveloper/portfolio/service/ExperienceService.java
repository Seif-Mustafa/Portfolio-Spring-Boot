package com.thegooddeveloper.portfolio.service;

import com.thegooddeveloper.portfolio.dto.request.CreateExperienceRequest;
import com.thegooddeveloper.portfolio.entity.Experience;

public interface ExperienceService {
  Experience createExperience(CreateExperienceRequest request);
}
