package com.studymavernspringboot.mustachajax.sblike;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SbLikeServiceImpl implements ISbLikeService {
    @Autowired
    private ISbLikeMybatisMapper boardLikeMybatisMapper;

    @Override
    public ISbLike insert(CUDInfoDto info, ISbLike dto) {
        if ( dto == null ) {
            return null;
        }
        SbLikeDto insert = SbLikeDto.builder().id(0L).build();
        insert.copyFields(dto);
        boardLikeMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public ISbLike update(CUDInfoDto info, ISbLike dto) {
        return null;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto info, ISbLike dto) {
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        return false;
    }

    @Override
    public ISbLike findById(Long id) {
        return null;
    }

    @Override
    public Boolean deleteByTableUserBoard(SbLikeDto dto) {
        if ( dto == null || dto.getTbl() == null || dto.getTbl().isEmpty()
                || dto.getNickname() == null || dto.getNickname().isEmpty()
                || dto.getBoardId() == null || dto.getBoardId() <= 0 ) {
            return false;
        }
        this.boardLikeMybatisMapper.deleteByTableUserBoard(dto);
        return true;
    }

    @Override
    public Integer countByTableUserBoard(ISbLike searchDto) {
        if ( searchDto == null || searchDto.getTbl() == null || searchDto.getTbl().isEmpty()
                || searchDto.getNickname() == null || searchDto.getNickname().isEmpty()
                || searchDto.getBoardId() == null || searchDto.getBoardId() <= 0 ) {
            return 0;
        }
        SbLikeDto search = SbLikeDto.builder().build();
        search.copyFields(searchDto);
        Integer count = this.boardLikeMybatisMapper.countByTableUserBoard(search);
        return count;
    }
}
