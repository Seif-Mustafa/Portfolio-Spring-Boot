package com.thegooddeveloper.portfolio.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CreateExperienceRequest {
    private Long userId;
    private String title;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String experianceDetail; // matches your DB column name
}