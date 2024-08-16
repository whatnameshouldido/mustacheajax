package com.studymavernspringboot.mustachajax.sbfile;

import com.studymavernspringboot.mustachajax.board.BoardDto;
import com.studymavernspringboot.mustachajax.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ISbFileService extends IServiceCRUD<ISbFile> {
    List<ISbFile> findAllByTblBoardId(ISbFile search);
    Boolean insertFiles(BoardDto boardDto, MultipartFile[] files);
}
