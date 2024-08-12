package com.studymavernspringboot.mustachajax.commons.inif;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;

public interface IServiceCRUD<T> {
    T insert(CUDInfoDto info, T dto);
    T update(CUDInfoDto info, T dto);
    Boolean deleteFlag(CUDInfoDto info, T dto);
    Boolean deleteById(Long id);
    T findById(Long id);
}
