package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpHeaders;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardWebController {

    @GetMapping("/board_ajx_list")
    private String boardAjxList(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session
    ) {
        IMember loginUser = (IMember)model.getAttribute("loginUser");
        if ( loginUser == null ) {
            return "redirect:/";
        }
        return "board/board_ajx_list";
    }
}
