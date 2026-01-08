package com.thegooddeveloper.portfolio.service.impl;

import com.thegooddeveloper.portfolio.dto.CategoryDTO;
import com.thegooddeveloper.portfolio.dto.request.CreateCategoryRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCategoryRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.entity.UserInfo;
import com.thegooddeveloper.portfolio.mapper.CategoryMapper;
import com.thegooddeveloper.portfolio.repository.CategoryRepository;
import com.thegooddeveloper.portfolio.repository.UserInfoRepository;
import com.thegooddeveloper.portfolio.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final UserInfoRepository userInfoRepository;
  private final CategoryMapper categoryMapper;

  public List<CategoryDTO> getCategoriesByFilters(Long categoryId, Long userId, String isHidden) {
    List<Category> categories = categoryRepository.getCategoriesByFilters(categoryId, userId, isHidden);
    return categoryMapper.toListDto(categories);
  }

  public Category createCategory(CreateCategoryRequest request) {
    UserInfo user = userInfoRepository.findById(request.getUserId())
        .orElseThrow(() -> new RuntimeException("User not found"));

    Category category = new Category();
    category.setUserInfo(user);
    category.setIsHidden("N");
    category.setCategoryName(request.getCategoryName());
    return categoryRepository.save(category);
  }

  public Category updateCategory(Long categoryId, UpdateCategoryRequest request) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new RuntimeException("Category not found"));

    if (request.getCategoryName() != null) {
      category.setCategoryName(request.getCategoryName());
    }

    if (request.getIsHidden() != null) {
      category.setIsHidden(request.getIsHidden());
    }

    return categoryRepository.save(category);
  }

  public void deleteCategory(Long categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new RuntimeException("Category not found");
    }
    categoryRepository.deleteById(categoryId);
  }

}
