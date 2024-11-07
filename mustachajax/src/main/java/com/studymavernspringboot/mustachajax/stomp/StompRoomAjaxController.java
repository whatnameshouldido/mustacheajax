package com.studymavernspringboot.mustachajax.stomp;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/stomp")
public class StompRoomAjaxController {
    @Autowired
    private StompRoomService stompRoomService;

    @PostMapping("/create")
    public ResponseEntity<StompRoomDto> createStompRoom(Model model, @RequestBody StompRoomDto stompRoomDto) {
        if (stompRoomDto == null) {
            return ResponseEntity.badRequest().build();
        }
        StompRoomDto newRoom = this.stompRoomService.insert(stompRoomDto.getRoomName(), stompRoomDto.getWriter());
        return ResponseEntity.ok(newRoom);
    }

}
