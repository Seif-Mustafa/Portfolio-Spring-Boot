package com.thegooddeveloper.portfolio.mapper;

import org.springframework.stereotype.Component;

import com.thegooddeveloper.portfolio.dto.request.CreateExperienceRequest;
import com.thegooddeveloper.portfolio.entity.Experience;
import com.thegooddeveloper.portfolio.entity.UserInfo;

@Component
public class ExperienceMapper {
  public Experience toEntity(UserInfo user, CreateExperienceRequest request) {
    Experience experience = new Experience();
    experience.setUserInfo(user);
    experience.setTitle(request.getTitle());
    experience.setDateFrom(request.getDateFrom());
    experience.setDateTo(request.getDateTo());
    experience.setExperianceDetail(request.getExperianceDetail());
    return experience;
  }
}
