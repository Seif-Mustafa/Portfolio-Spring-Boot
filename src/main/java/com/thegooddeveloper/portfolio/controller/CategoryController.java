package com.thegooddeveloper.portfolio.controller;

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

import com.thegooddeveloper.portfolio.dto.CategoryDTO;
import com.thegooddeveloper.portfolio.dto.generic.ApiResponse;
import com.thegooddeveloper.portfolio.dto.request.CreateCategoryRequest;
import com.thegooddeveloper.portfolio.dto.request.UpdateCategoryRequest;
import com.thegooddeveloper.portfolio.entity.Category;
import com.thegooddeveloper.portfolio.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ApiResponse<List<CategoryDTO>> getCategoriesByFilters(
      @RequestParam(value = "categoryId", required = false) Long categoryId,
      @RequestParam(value = "userId", required = false) Long userId,
      @RequestParam(value = "isHidden", required = false) String isHidden
    ) {
    return ApiResponse.success(categoryService.getCategoriesByFilters(categoryId, userId,isHidden),
        "Categories Returned Successfully");
  }

  @PostMapping
  public ApiResponse<Category> createCategory(@RequestBody CreateCategoryRequest request) {
    return ApiResponse.created(categoryService.createCategory(request), "Category Created Successfully");
  }

  @PutMapping("/{categoryId}")
  public ApiResponse<Category> updateCategory(
      @PathVariable Long categoryId,
      @RequestBody UpdateCategoryRequest request) {
    return ApiResponse.success(categoryService.updateCategory(categoryId, request), "Category Updated Successfully");
  }


    @DeleteMapping("/{categoryId}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long categoryId) {
      categoryService.deleteCategory(categoryId);
        return ApiResponse.noContent( "Category Deleted Successfully");
    }

}
