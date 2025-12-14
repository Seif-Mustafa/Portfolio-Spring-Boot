package com.thegooddeveloper.portfolio.dto.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private Long userId;
    private String categoryName;
}