package com.studymavernspringboot.mustachajax.category;

import com.studymavernspringboot.mustachajax.ICommonService;

import java.util.List;

public interface ICategoryService<T> extends ICommonService<T> {
    ICategory findByName(String name);
    List<ICategory> findAllByNameContains(SearchCategoryDto dto);
    int countAllByNameContains(SearchCategoryDto searchCategoryDto);
}