package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.ResponseDto;
import com.studymavernspringboot.mustachajax.sblike.SbLikeDto;
import com.studymavernspringboot.mustachajax.sblike.ISbLikeService;
import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.MemberRole;
import com.studymavernspringboot.mustachajax.sbfile.ISbFile;
import com.studymavernspringboot.mustachajax.sbfile.ISbFileService;
import com.studymavernspringboot.mustachajax.sbfile.SbFileDto;
import com.studymavernspringboot.mustachajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/board")
public class BoardApiController {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private ISbLikeService boardLikeService;

    @Autowired
    private ISbFileService sbFileService;

    @PostMapping
    public ResponseEntity<ResponseDto> insert(Model model
            , @Validated @RequestPart(value="boardDto") BoardDto dto
            , @RequestPart(value="files", required = false) MultipartFile[] files
    ) {
        try {
            if ( dto == null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            BoardDto result = this.boardService.insert(cudInfoDto, dto);
            if ( result == null ) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            this.sbFileService.insertFiles(result, files);
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @PatchMapping("")
    public ResponseEntity<ResponseDto> update(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IBoard find = this.boardService.findById(dto.getId());
            if (find == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("데이터 없음").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getNickname().equals(find.getCreateId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("관리자와 본인만 수정").build());
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            IBoard result = this.boardService.update(cudInfoDto, dto);
            if ( result == null ) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @DeleteMapping("/delflag")
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @RequestBody BoardDto dto) {
        try {
            if ( dto == null || dto.getId() == null || dto.getId() <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IBoard find = this.boardService.findById(dto.getId());
            if (find == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("데이터 없음").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            } else if (!loginUser.getRole().equals(MemberRole.ADMIN.toString()) && !loginUser.getNickname().equals(find.getCreateId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("관리자와 본인만 삭제").build());
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            Boolean result = this.boardService.updateDeleteFlag(cudInfoDto, dto);
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IBoard find = this.boardService.findById(id);
            if (find == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("데이터 없음").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            if ( !loginUser.getRole().equals(MemberRole.ADMIN.toString()) ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("관리자만 가능").build());
            }
            Boolean result = this.boardService.deleteById(id);
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @PathVariable Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            this.boardService.addViewQty(id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            if ( searchAjaxDto == null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            ResponseDto res = ResponseDto.builder().message("ok").result(total).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @PostMapping("/searchName")
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @RequestBody SearchAjaxDto searchAjaxDto) {
        try {
            if ( searchAjaxDto == null ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            int total = this.boardService.countAllByNameContains(searchAjaxDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchAjaxDto);
            if ( list == null ) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            searchAjaxDto.setTotal(total);
            searchAjaxDto.setDataList(list);
            ResponseDto res = ResponseDto.builder().message("ok").result(searchAjaxDto).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @PathVariable Long id) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.addLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @PathVariable Long id) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            this.boardService.subLikeQty(cudInfoDto, id);
            IBoard result = this.getBoardAndLike(id, loginUser);
            if ( result == null ) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseDto.builder().message("에러 관리자 문의").build());
            }
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    private IBoard getBoardAndLike(Long id, IMember loginUser) {
        IBoard result = this.boardService.findById(id);
        if ( result == null ) {
            return null;
        }
        SbLikeDto boardLikeDto = SbLikeDto.builder()
                .tbl("board")
                .nickname(loginUser.getNickname())
                .boardId(id)
                .build();
        Integer likeCount = this.boardLikeService.countByTableUserBoard(boardLikeDto);
        result.setDeleteDt(likeCount.toString());
        return result;
    }

    @GetMapping("/files/{tbl}/{boardId}")
    public ResponseEntity<ResponseDto> getFileList(Model model
            , @PathVariable String tbl, @PathVariable Long boardId) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDto.builder().message("로그인 필요").build());
            }
            if ( tbl == null || tbl.isEmpty() || boardId == null || boardId <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ResponseDto.builder().message("입력값 비정상").build());
            }
            SbFileDto search = SbFileDto.builder()
                    .tbl(tbl).boardId(boardId).build();
            List<ISbFile> result = this.sbFileService.findAllByTblBoardId(search);
            ResponseDto res = ResponseDto.builder().message("ok").result(result).build();
            return ResponseEntity.ok(res);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseDto.builder().message(ex.getMessage()).build());
        }
    }

    @GetMapping("/file/{tbl}/{name}/{uniqName}/{fileType}")
    public ResponseEntity<ByteArrayResource> downloadFile(Model model
            , @PathVariable String tbl, @PathVariable String name
            , @PathVariable String uniqName, @PathVariable String fileType) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( name == null || name.isEmpty() || uniqName == null || uniqName.isEmpty() || fileType == null || fileType.isEmpty() ) {
                return ResponseEntity.badRequest().build();
            }
            SbFileDto down = SbFileDto.builder()
                    .tbl(tbl).name(name).uniqName(uniqName).fileType(fileType).build();
            byte[] bytes = this.sbFileService.getBytesFromFile(down);
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity.ok()
                    .contentLength(bytes.length)
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, StandardCharsets.UTF_8))
                    .body(resource);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/fileId/{id}")
    public ResponseEntity<ByteArrayResource> downloadFileId(Model model
            , @PathVariable Long id) {
        try {
            IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
            if ( loginUser == null ) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            if ( id == null || id <= 0 ) {
                return ResponseEntity.badRequest().build();
            }
            ISbFile find = this.sbFileService.findById(id);
            if ( find == null ) {
                return ResponseEntity.notFound().build();
            }
            byte[] bytes = this.sbFileService.getBytesFromFile(find);
            ByteArrayResource resource = new ByteArrayResource(bytes);
            return ResponseEntity.ok()
                    .contentLength(bytes.length)
                    .header("Content-Type", "application/octet-stream")
                    .header("Content-Disposition", "attachment; filename=" + URLEncoder.encode(find.getName(), StandardCharsets.UTF_8))
                    .body(resource);
        } catch ( Exception ex ) {
            log.error(ex.toString());
            return ResponseEntity.internalServerError().build();
        }
    }
}