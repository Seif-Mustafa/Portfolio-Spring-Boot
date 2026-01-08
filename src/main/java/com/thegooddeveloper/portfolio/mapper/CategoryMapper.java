package com.thegooddeveloper.portfolio.mapper;

import com.thegooddeveloper.portfolio.dto.CategoryDTO;
import com.thegooddeveloper.portfolio.entity.Category;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

    public CategoryDTO toDto(Category category) {
        return CategoryDTO.
                builder().
                categoryId(category.getCategoryId()).
                isHidden(category.getIsHidden()).
                categoryName(category.getCategoryName()).
                build();
    }

    public List<CategoryDTO> toListDto(List<Category> categories) {
        return categories.stream().map(
                (category) ->
                        toDto(category)
        ).collect(Collectors.toList());
    }
}
