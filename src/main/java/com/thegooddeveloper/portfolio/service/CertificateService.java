package com.thegooddeveloper.portfolio.service;

import java.util.List;

import com.thegooddeveloper.portfolio.dto.CertificateDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCertificateRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCertificateRequest;
import com.thegooddeveloper.portfolio.entity.Certificate;

public interface CertificateService {
  List<CertificateDTO> getCertificatesWithFilters(Long categoryId, Long userId, String isMajor);

  Certificate createCertificate(CreateCertificateRequest request);

  Certificate updateCertificate(Long certificateId, UpdateCertificateRequest request);

  void deleteCertificate(Long certificateId);
}
