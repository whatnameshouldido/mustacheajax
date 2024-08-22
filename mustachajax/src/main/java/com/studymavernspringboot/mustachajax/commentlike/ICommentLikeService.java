package com.studymavernspringboot.mustachajax.commentlike;

import com.studymavernspringboot.mustachajax.commons.inif.IServiceCRUD;

public interface ICommentLikeService extends IServiceCRUD<ICommentLike> {
    Boolean deleteByCommentTableUserBoard(CommentLikeDto dto);
    Integer countByCommentTableUserBoard(ICommentLike searchDto);
}
