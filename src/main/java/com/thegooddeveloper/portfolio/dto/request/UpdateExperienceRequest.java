package com.thegooddeveloper.portfolio.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UpdateExperienceRequest {
    private String title;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String experianceDetail;
}
