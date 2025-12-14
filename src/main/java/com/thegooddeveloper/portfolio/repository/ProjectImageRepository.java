package com.thegooddeveloper.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thegooddeveloper.portfolio.entity.ProjectImage;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    List<ProjectImage> findByProjectProjectId(Long projectId);
}
