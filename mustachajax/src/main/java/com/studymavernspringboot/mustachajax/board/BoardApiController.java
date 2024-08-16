package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.boardlike.BoardLikeDto;
import com.studymavernspringboot.mustachajax.boardlike.IBoardLikeService;
import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
import com.studymavernspringboot.mustachajax.filecntl.FileCtrlService;
import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.MemberRole;
import com.studymavernspringboot.mustachajax.sbfile.ISbFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private IBoardLikeService boardLikeService;

    @Autowired
    private ISbFileService sbFileService;

    @PostMapping
    public ResponseEntity<IBoard> insert(Model model
            , @RequestPart(value="boardDto") BoardDto dto
            , @RequestPart(value="files", required = false) MultipartFile[] files
    ) {
        try {
            if ( dto == null ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            BoardDto result = this.boardService.insert(cudInfoDto, dto);
            if ( result == null ) {
                return ResponseEntity.badRequest().build();
            }
            this.sbFileService.insertFiles(result, files);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("")
    public ResponseEntity<IBoard> update(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.update(cudInfoDto, dto);
            if ( result == null ) {
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
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            Boolean result = this.boardService.deleteById(id);
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<IBoard> findById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            this.boardService.addViewQty(id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<SearchAjaxDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build();
            }
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            return ResponseEntity.ok(searchAjaxDto);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<Integer> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            return ResponseEntity.ok(total);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<IBoard> addLikeQty(Model model, @PathVariable Long id) {
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.addLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<IBoard> subLikeQty(Model model, @PathVariable Long id) {
        try {
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.subLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(result);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    private IBoard getBoardAndLike(Long id, IMember loginUser) {
        IBoard result = this.boardService.findById(id);
        if ( result == null ) {
            return null;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .tbl("board")
                .likeUserId(loginUser.getLoginId())
                .boardId(id)
                .build();
        Integer likeCount = this.boardLikeService.countByTableUserBoard(boardLikeDto);
        result.setDelFlag(likeCount.toString());
        return result;
    }
}