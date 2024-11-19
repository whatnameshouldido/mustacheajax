package com.studymavernspringboot.mustachajax.stompevery;

import com.studymavernspringboot.mustachajax.stomp.StompMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class StompEveryChatController {
    @Autowired
    private SimpMessageSendingOperations msgTempate;

    @Autowired
    private StompEveryRoomService stompEveryRoomService;

    @MessageMapping("/stompevery/message")
    public void message(StompEveryMessageDto StompEveryMessageDto) {
        log.info("/stompevery/message => roomId:{}, msgType:{}, writer:{}, message:{}"
                , StompEveryMessageDto.getRoomId()
                , StompEveryMessageDto.getMsgType()
                , StompEveryMessageDto.getWriter()
                , StompEveryMessageDto.getMessage()
        );
        StompEveryRoomDto stompRoom = stompEveryRoomService.findByRoomId(StompEveryMessageDto.getRoomId());
        if (stompRoom == null) {
            return;
        }
        if ( StompMessageType.ENTER == StompEveryMessageDto.getMsgType() ) {
//            stompRoom.getUserList().add(StompEveryMessageDto.getWriter());
            stompRoom.setCount(stompRoom.getCount() + 1);
            stompEveryRoomService.update(stompRoom);
        } else if ( StompMessageType.OUT == StompEveryMessageDto.getMsgType() ) {
//            stompRoom.getUserList().remove(StompEveryMessageDto.getWriter());
            stompRoom.setCount(stompRoom.getCount() - 1);
            stompEveryRoomService.update(stompRoom);
        }
        if( stompRoom.getCount() < 1 ) {
            stompEveryRoomService.deleteByRoomId(StompEveryMessageDto.getRoomId());
        } else {
            msgTempate.convertAndSend("/sub/stompevery/room/" + StompEveryMessageDto.getRoomId()
                    , StompEveryMessageDto);
        }
    }
}
