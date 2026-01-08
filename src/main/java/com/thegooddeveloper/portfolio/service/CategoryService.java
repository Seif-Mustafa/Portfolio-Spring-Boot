package com.thegooddeveloper.portfolio.service;

import com.thegooddeveloper.portfolio.dto.CategoryDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCategoryRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCategoryRequest;
import com.thegooddeveloper.portfolio.entity.Category;


import java.util.List;

public interface CategoryService {

  List<CategoryDTO> getCategoriesByFilters(Long categoryId, Long userId,String isHidden);

  Category createCategory(CreateCategoryRequest request);

  Category updateCategory(Long categoryId, UpdateCategoryRequest request);

  void deleteCategory(Long categoryId);

}
