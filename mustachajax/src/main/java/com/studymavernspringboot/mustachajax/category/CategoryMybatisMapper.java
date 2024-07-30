package com.studymavernspringboot.mustachajax.category;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMybatisMapper {
    void insert(CategoryDto dto);

    CategoryDto findById(long id);

    CategoryDto findByName(String name);

    List<CategoryDto> findAll();

    void deleteById(Long id);

    void update(CategoryDto dto);

    int countAllByNameContains(SearchCategoryDto searchCategoryDto);
    List<CategoryDto> findAllByNameContains(SearchCategoryDto searchCategoryDto);
}
