package com.studymavernspringboot.mustachajax.sbfile;

import com.studymavernspringboot.mustachajax.board.BoardDto;
import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.filecntl.FileCtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class SbFileServiceImpl implements ISbFileService {
    @Autowired
    private ISbFileMybatisMapper sbFileMybatisMapper;

    @Autowired
    private FileCtrlService fileCtrlService;

    @Override
    public ISbFile insert(CUDInfoDto info, ISbFile dto) {
        if ( dto == null ) {
            return null;
        }
        SbFileDto insert = SbFileDto.builder().build();
        insert.copyFields(dto);
        this.sbFileMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public ISbFile update(CUDInfoDto info, ISbFile dto) {
        return null;
    }

    @Override
    public Boolean deleteFlag(CUDInfoDto info, ISbFile dto) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return false;
        }
        this.sbFileMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public ISbFile findById(Long id) {
        return null;
    }

    @Override
    public List<ISbFile> findAllByTblBoardId(ISbFile search) {
        if ( search == null ) {
            return null;
        }
        return List.of();
    }

    @Override
    public Boolean insertFiles(BoardDto boardDto, MultipartFile[] files) {
        if ( boardDto == null || files == null || files.length <= 0 ) {
            return false;
        }
        int ord = 0;
        for ( MultipartFile file : files ) {
            SbFileDto insert = SbFileDto.builder()
                    .name(file.getOriginalFilename())
                    .ord(ord++)
                    .fileType(this.getFileType(Objects.requireNonNull(file.getOriginalFilename())))
                    .uniqName(UUID.randomUUID().toString())
                    .length((int)file.getSize())
                    .tbl("board")
                    .boardId(boardDto.getId())
                    .build();
            try {
                this.sbFileMybatisMapper.insert(insert);
                this.fileCtrlService.saveFile(file, insert.getUniqName() + insert.getFileType());
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }
        return true;
    }

    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
