package com.thegooddeveloper.portfolio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thegooddeveloper.portfolio.entity.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long>{

  List<Certificate> findByUserInfoUserId(Long userId);

  @Query("SELECT c FROM Certificate c WHERE c.userInfo.userId = :userid AND c.isMajor = 'Y'")
  List<Certificate> findMajorByUserId(@Param("userId") Long userId);

  @Query("SELECT c FROM Certificate c WHERE c.userInfo.userId = :userId AND c.certificateCategory.categoryId = :categoryId")
  List<Certificate> findByUserIdAndCategoryId(@Param("userId") Long userId, @Param("categoryId") Long categoryId);

}
