package com.thegooddeveloper.portfolio.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thegooddeveloper.portfolio.dto.CertificateDTO;
import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import com.thegooddeveloper.portfolio.dto.request.CreateCertificateRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCertificateRequest;
import com.thegooddeveloper.portfolio.entity.Certificate;
import com.thegooddeveloper.portfolio.service.CertificateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
  private final CertificateService certificateService;

  @GetMapping
  public ApiResponse<List<CertificateDTO>> getCertificatesWithFilters(
      @RequestParam(value = "categoryId", required = false) Long categoryId,
      @RequestParam(value = "userId", required = false) Long userId,
      @RequestParam(value = "isMajor", required = false) String isMajor) {
    return ApiResponse.success(certificateService.getCertificatesWithFilters(categoryId, userId, isMajor),
        "Certificates Returned Successfully");
  }

  @PostMapping
  public ApiResponse<Certificate> createCertificate(
      @RequestBody CreateCertificateRequest request) {
    return ApiResponse.created(certificateService.createCertificate(request), "Certificate Created Successfully");
  }

  @PutMapping("/{certificateId}")
  public ApiResponse<Certificate> updateCertificate(
      @PathVariable Long certificateId,
      @RequestBody UpdateCertificateRequest request) throws IOException {
    return ApiResponse.success(certificateService.updateCertificate(certificateId, request),
        "Certificate Updated Successfully");
  }

  @DeleteMapping("/{certificateId}")
  public ApiResponse<Void> deleteCertificate(@PathVariable Long certificateId) {
    certificateService.deleteCertificate(certificateId);
    return ApiResponse.noContent("Certificate Deleted Successfully");
  }

}
