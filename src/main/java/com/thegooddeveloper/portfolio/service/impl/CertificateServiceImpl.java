package com.thegooddeveloper.portfolio.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thegooddeveloper.portfolio.dto.CertificateDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCertificateRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCertificateRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Certificate;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.mapper.CertificateMapper;
import com.thegooddeveloper.portfolio.repository.CategoryRepository;
import com.thegooddeveloper.portfolio.repository.CertificateRepository;
import com.thegooddeveloper.portfolio.repository.UserInfoRepository;
import com.thegooddeveloper.portfolio.service.CertificateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

  private final CertificateRepository certificateRepository;
  private final UserInfoRepository userInfoRepository;
  private final CategoryRepository categoryRepository;
  private final CertificateMapper certificateMapper;

  @Override
  public List<CertificateDTO> getCertificatesWithFilters(Long categoryId, Long userId, String isMajor) {

    return certificateMapper.toListDto(certificateRepository.getCertificatesWithFilters(categoryId, userId, isMajor));
  }

  @Override
  public Certificate createCertificate(CreateCertificateRequest request) {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new RuntimeException("Category not found"));
    Certificate certificate = certificateMapper.toEntity(request, user, category);
    return certificateRepository.save(certificate);

  }

  public Certificate updateCertificate(Long certificateId, UpdateCertificateRequest request) {
    Certificate certificate = certificateRepository.findById(certificateId)
        .orElseThrow(() -> new RuntimeException("Certificate not found"));

    if (request.getCategoryId() != null) {
      Category category = categoryRepository.findById(request.getCategoryId())
          .orElseThrow(() -> new RuntimeException("Category not found"));
      certificate.setCertificateCategory(category);
    }

    if (request.getVerificationLink() != null) {
      certificate.setVerificationLink(request.getVerificationLink());
    }

    if (request.getIsMajor() != null) {
      certificate.setIsMajor(request.getIsMajor());
    }

    if (request.getImageLink() != null) {
      certificate.setImageLink(request.getImageLink());
    }

    // if (request.getImage() != null && !request.getImage().isEmpty()) {
    // certificate.setImage(request.getImage().getBytes());
    // }

    return certificateRepository.save(certificate);
  }

  public void deleteCertificate(Long certificateId) {
    if (!certificateRepository.existsById(certificateId)) {
      throw new RuntimeException("Certificate not found");
    }
    certificateRepository.deleteById(certificateId);
  }
}
