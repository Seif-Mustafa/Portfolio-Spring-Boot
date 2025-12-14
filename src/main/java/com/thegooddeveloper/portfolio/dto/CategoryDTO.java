package com.thegooddeveloper.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTO {
  private Long categoryId;
  private String categoryName;

}
