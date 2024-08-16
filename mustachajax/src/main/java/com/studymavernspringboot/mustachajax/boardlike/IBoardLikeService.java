package com.studymavernspringboot.mustachajax.boardlike;

import com.studymavernspringboot.mustachajax.commons.inif.IServiceCRUD;

public interface IBoardLikeService extends IServiceCRUD<IBoardLike> {
    Boolean deleteByTableUserBoard(BoardLikeDto dto);
    Integer countByTableUserBoard(IBoardLike searchDto);
}
