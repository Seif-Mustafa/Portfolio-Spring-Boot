package com.thegooddeveloper.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryDTO {
  private Long categoryId;
  private String isHidden;
  private String categoryName;

}
