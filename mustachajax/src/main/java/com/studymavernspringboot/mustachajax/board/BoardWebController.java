package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.member.IMember;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardWebController {
    @Autowired
    private com.studymavernspringboot.mustachajax.board.IBoardService boardService;

    @GetMapping("/board_ajx_list")
    public String boardAjxList(Model model) {
        IMember loginUser = (IMember)model.getAttribute("loginUser");
        if ( loginUser == null ) {
            return "redirect:/";
        }
        return "board/board_ajx_list";
    }

    @GetMapping("/add")
    public String addScreen(Model model) {
        IMember loginUser = (IMember)model.getAttribute("loginUser");
        if ( loginUser == null ) {
            return "redirect:/";
        }
        return "board/board_add";
    }

    @PostMapping("/insert")
    public String insert(Model model, @ModelAttribute BoardRequest reqDto) {
        try {
            if ( reqDto == null ) {
                return "redirect:/";
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return "redirect:/";
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            // 로그인한 사용자의 정보를 insert 할때 전달한다.
            this.boardService.insert(cudInfoDto, reqDto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/board/add";
    }

    @GetMapping("/view")
    public String viewScreen(Model model, @RequestParam Long id) {
        IMember loginUser = (IMember)model.getAttribute("loginUser");
        if ( loginUser == null ) {
            return "redirect:/";
        }
        IBoard find = this.boardService.findById(id);
        if ( find == null ) {
            return "redirect:/";
        }
        model.addAttribute("findDto", find);
        model.addAttribute("LoginEqBoard",(loginUser.getLoginId().equals(find.getCreateId()) ? true : null) );
        // this.boardService.addViewQty(id);    // 좋아요 +1 동작
        return "board/board_view";
        // board/board_view.html 안의 {{#findDto}} ~~{{name}}~~ {{/findDto}}
        // {{#LoginEqBoard}} ~~<button id='updateBtn'>수정</button>~~ {{/LoginEqBoard}}
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute BoardRequest reqDto) {
        try {
            if ( reqDto == null ) {
                return "redirect:/";
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return "redirect:/";
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            // 로그인한 사용자의 정보를 update 할때 전달한다.
            this.boardService.update(cudInfoDto, reqDto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/board/add";
    }

    @GetMapping("/delete")
    public String delete(Model model, @RequestParam Long id) {
        try {
            if ( id == null || id <= 0 ) {
                return "redirect:/";
            }
            IMember loginUser = (IMember)model.getAttribute("loginUser");
            if ( loginUser == null ) {
                return "redirect:/";
            }
            BoardDto dto = BoardDto.builder().id(id).build();
            CUDInfoDto cudInfoDto = new CUDInfoDto(loginUser);
            // 로그인한 사용자의 정보를 deleteFlag 할때 전달한다.
            this.boardService.deleteFlag(cudInfoDto, dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/board/add";
    }
}
