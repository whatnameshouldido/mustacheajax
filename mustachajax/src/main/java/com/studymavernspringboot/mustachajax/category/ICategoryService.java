package com.studymavernspringboot.mustachajax.category;

import com.studymavernspringboot.mustachajax.ICommonService;
import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;

import java.util.List;

public interface ICategoryService<T> extends ICommonService<T> {
    ICategory findByName(String name);
    List<ICategory> findAllByNameContains(SearchAjaxDto dto);
    int countAllByNameContains(SearchAjaxDto searchAjaxDto);
}