package com.studymavernspringboot.mustachajax.stompevery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class StompEveryRoomService {
    @Autowired
    private IStompEveryRoomMybatisMapper iStompEveryRoomMybatisMapper;

    @Autowired
    private IStompEveryChatMybatisMapper iStompEveryChatMybatisMapper;

    public StompEveryRoomDto insert(String roomName) {
        StompEveryRoomDto newRoom = StompEveryRoomDto.builder()
                .id(-1L)
                .roomName(roomName)
                .build();
        iStompEveryRoomMybatisMapper.insert(newRoom);
        return newRoom;
    }

    public StompEveryRoomDto findByRoomId(Long id) {
        return iStompEveryRoomMybatisMapper.findById(id);
    }

    public List<StompEveryRoomDto> findAll() {
        return iStompEveryRoomMybatisMapper.findAll();
    }

    public void update(StompEveryRoomDto dto) {
        iStompEveryRoomMybatisMapper.update(dto);
    }

    @Transactional
    public void deleteByRoomId(Long id, StompEveryChatDto chatDto) {
        iStompEveryRoomMybatisMapper.deleteFlagById(id);
        iStompEveryChatMybatisMapper.insert(chatDto);
    }
}
