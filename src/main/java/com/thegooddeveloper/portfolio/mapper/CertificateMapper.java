package com.thegooddeveloper.portfolio.mapper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.thegooddeveloper.portfolio.dto.CertificateDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCertificateRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.Certificate;
import com.thegooddeveloper.portfolio.entity.UserInfo;

@Component
public class CertificateMapper {

  public Certificate toEntity(CreateCertificateRequest request,
      UserInfo user, Category category) {
    Certificate certificate = new Certificate();
    certificate.setUserInfo(user);
    certificate.setCertificateCategory(category);
    certificate.setImageLink(request.getImageLink());
    certificate.setVerificationLink(request.getVerificationLink());
    certificate.setIsMajor(request.getIsMajor());
    // if (request.getImage() != null && !request.getImage().isEmpty()) {
    //   certificate.setImage(request.getImage().getBytes());
    // } 
    // else {
    //   throw new IllegalArgumentException("Certificate image is required");
    // }
    return certificate;
  }

  public CertificateDTO toDto(Certificate certificate) {
    return CertificateDTO.builder().certificateId(certificate.getCertificateId())
        .categoryId(certificate.getCertificateCategory().getCategoryId())
        .categoryName(certificate.getCertificateCategory().getCategoryName())
        .verificationLink(certificate.getVerificationLink()).isMajor(certificate.getIsMajor())
        .imageLink(certificate.getImageLink()).build();
  }

  public List<CertificateDTO> toListDto(List<Certificate> certificates) {
    return certificates.stream().map(
        (certificate) -> toDto(certificate)).collect(Collectors.toList());
  }

}
