package com.thegooddeveloper.portfolio.dto.request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {
    private String categoryName;
    private String isHidden;

}
