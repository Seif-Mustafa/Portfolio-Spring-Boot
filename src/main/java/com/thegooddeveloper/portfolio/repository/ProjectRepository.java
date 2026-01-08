package com.thegooddeveloper.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thegooddeveloper.portfolio.entity.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.userInfo.userId = :userId ORDER BY p.projectPriority")
    List<Project> findByUserInfoUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Project p WHERE p.userInfo.userId = :userId AND p.projectCategory.categoryId = :categoryId order by p.projectPriority DESC")
    List<Project> findByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

    @Query("""
            SELECT p FROM Project p 
            WHERE ( :userId IS NULL OR p.userInfo.userId = :userId )
            AND ( :categoryId IS NULL OR p.projectCategory.categoryId = :categoryId)
            AND ( :projectId IS NULL OR p.projectId = :projectId)
            AND ( :isHidden IS NULL OR p.isHidden = :isHidden)
            order by p.projectPriority DESC
            """)
    List<Project> getAllProjectsByOptionalFilters(@Param("userId") Long userId,
                                                  @Param("categoryId") Long categoryId,
                                                  @Param("projectId") Long projectId,
                                                  @Param("isHidden") String isHidden);


    @Modifying
    @Query("""
            UPDATE Project p 
                SET p.isHidden = :isHidden
            WHERE ( :userId IS NULL OR p.userInfo.userId = :userId)
            AND ( :categoryId IS NULL OR p.projectCategory.categoryId = :categoryId)
            AND ( :projectId IS NULL OR p.projectId = :projectId)
            """)
    int changeProjectVisibility(@Param("userId") Long userId, @Param("categoryId") Long categoryId, @Param("projectId") Long projectId, @Param("isHidden") String isHidden);
}
