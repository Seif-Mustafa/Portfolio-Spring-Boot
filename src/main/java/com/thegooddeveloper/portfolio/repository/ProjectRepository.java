package com.thegooddeveloper.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thegooddeveloper.portfolio.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

  @Query("SELECT p FROM Project p WHERE p.userInfo.userId = :userId ORDER BY p.projectPriority")
  List<Project> findByUserInfoUserId(@Param("userId") Long userId);

  @Query("SELECT p FROM Project p WHERE p.userInfo.userId = :userId AND p.projectCategory.categoryId = :categoryId order by p.projectPriority DESC")
  List<Project> findByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

}
