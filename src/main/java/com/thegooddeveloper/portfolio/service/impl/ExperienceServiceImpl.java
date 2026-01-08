package com.thegooddeveloper.portfolio.service.impl;

import org.springframework.stereotype.Service;

import com.thegooddeveloper.portfolio.dto.request.CreateExperienceRequest;
import com.thegooddeveloper.portfolio.entity.Experience;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.mapper.ExperienceMapper;
import com.thegooddeveloper.portfolio.repository.ExperienceRepository;
import com.thegooddeveloper.portfolio.repository.UserInfoRepository;
import com.thegooddeveloper.portfolio.service.ExperienceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {
  private final UserInfoRepository userInfoRepository;
  private final ExperienceRepository experienceRepository;
  private final ExperienceMapper experienceMapper;

  @Override
  public Experience createExperience(CreateExperienceRequest request) {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Experience exp = experienceMapper.toEntity(user, request);
    return experienceRepository.save(exp);
  }

}
