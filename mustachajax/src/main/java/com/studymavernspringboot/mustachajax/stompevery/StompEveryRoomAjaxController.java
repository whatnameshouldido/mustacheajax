package com.studymavernspringboot.mustachajax.stompevery;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.commons.exception.LoginAccessException;
import com.studymavernspringboot.mustachajax.commons.inif.IResponseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/stompevery")
public class StompEveryRoomAjaxController implements IResponseController {
    @Autowired
    private StompEveryRoomService stompEveryRoomService;

    @PostMapping("/create")
    public ResponseEntity<StompEveryRoomDto> createStompRoom(Model model, @RequestBody StompEveryRoomDto StompEveryRoomDto) {
        try {
            if (StompEveryRoomDto == null) {
                return ResponseEntity.badRequest().build();
            }
//            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            StompEveryRoomDto newRoom = this.stompEveryRoomService.insert(StompEveryRoomDto.getRoomName());
            return ResponseEntity.ok(newRoom);
        } catch (LoginAccessException lae) {
            log.error(lae.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
