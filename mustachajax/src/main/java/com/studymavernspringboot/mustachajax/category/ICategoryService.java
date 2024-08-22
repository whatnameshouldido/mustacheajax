package com.studymavernspringboot.mustachajax.category;

import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;

import java.util.List;

public interface ICategoryService<T> {
    T insert(T dto);
    T update(T dto);
    Boolean deleteById(Long id);
    T findById(Long id);
    List<T> getAllList();
    ICategory findByName(String name);
    List<ICategory> findAllByNameContains(SearchAjaxDto dto);
    int countAllByNameContains(SearchAjaxDto searchAjaxDto);
}