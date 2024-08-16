package com.studymavernspringboot.mustachajax.boardlike;

import com.studymavernspringboot.mustachajax.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMybatisMapper extends IMybatisCRUD<BoardLikeDto> {
    void deleteByTableUserBoard(BoardLikeDto dto);
    Integer countByTableUserBoard(BoardLikeDto searchDto);
}
