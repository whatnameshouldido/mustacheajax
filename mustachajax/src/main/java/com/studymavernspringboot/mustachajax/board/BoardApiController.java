package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.MemberRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    @Autowired
    private IBoardService boardService;

    @PostMapping //POST method : /cat
    public ResponseEntity<IBoard> insert(Model model, @RequestBody BoardDto dto) {
        try {
            if (dto == null) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.insert(cudInfoDto, dto);
            if (result == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("")
    public ResponseEntity<IBoard> update(Model model, @RequestBody BoardDto dto) {
        try {
            if (dto == null || dto.getId() == null || dto.getId() <= 0) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.update(cudInfoDto, dto);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delflag")
    public ResponseEntity<Boolean> deleteFlag(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.boardService.deleteFlag(cudInfoDto, dto);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }

    @DeleteMapping("/{id}") //DELETE method : /ct/번호
    public ResponseEntity<Boolean> deleteById(Model model, @PathVariable Long id) {
        try {
            if(id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if (loginUser == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if(!loginUser.getRole().equals(MemberRole.ADMIN.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Boolean result = this.boardService.deleteById(id);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<IBoard> findById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); // error 응답
            }
            this.boardService.addViewQty(id);

            IBoard result = this.boardService.findById(id);

            if(result == null) {
                return ResponseEntity.notFound().build(); // error 응답
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<SearchAjaxDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build(); // error 응답
            }
            searchAjaxDto.setTotal(total);
            // SearchBoardDto 응답결과에 total 을 추가한다.
            searchAjaxDto.setDataList(list);
            // SearchBoardDto 응답결과에 List<IBoard> 을 추가한다.
            return ResponseEntity.ok(searchAjaxDto);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }

    @PostMapping("/countName")  // POST method : /ct/countName
    public ResponseEntity<Integer> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            // POSTMAN 으로 테스트 안되지만, WEB 화면에서는 로그인한 사용자만 가능하다.
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build(); // error 응답
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            return ResponseEntity.ok(total);
            // 200 OK 와 result 데이터를 응답한다.
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<String> addLikeQty(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build(); // error 응답
            }
            this.boardService.addLikeQty(id);
            return ResponseEntity.ok("OK");
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build(); // error 응답
        }
    }
}
