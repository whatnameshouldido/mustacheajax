package com.studymavernspringboot.mustachajax.sblike;

import com.studymavernspringboot.mustachajax.commons.inif.IServiceCRUD;

public interface ISbLikeService extends IServiceCRUD<ISbLike> {
    Boolean deleteByTableUserBoard(SbLikeDto dto);
    Integer countByTableUserBoard(ISbLike searchDto);
}