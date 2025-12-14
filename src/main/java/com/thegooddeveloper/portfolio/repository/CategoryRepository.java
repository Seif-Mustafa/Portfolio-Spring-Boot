package com.thegooddeveloper.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thegooddeveloper.portfolio.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT DISTINCT c FROM Category c JOIN c.certificates cert WHERE c.userInfo.userId = :userId")
  List<Category> findCategoriesWithCertificates(@Param("userId") Long userId);

  @Query("SELECT DISTINCT c FROM Category c JOIN c.projects p WHERE c.userInfo.userId = :userId")
  List<Category> findCategoriesWithProjects(@Param("userId") Long userId);

  @Query("SELECT c FROM Category c WHERE c.userInfo.userId = :userId")
  List<Category> findByUserId(@Param("userId") Long userId);
}
