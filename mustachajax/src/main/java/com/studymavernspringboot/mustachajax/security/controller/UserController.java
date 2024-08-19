package com.studymavernspringboot.mustachajax.security.controller;

import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.IMemberService;
import com.studymavernspringboot.mustachajax.security.config.SecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("/infoCookie")
    private String showInfoCookie(Model model, @CookieValue(name = SecurityConfig.LOGINUSER, required = false) String loginId) {
        if ( loginId == null ) {
            return "redirect:/";
        }
        IMember loginUser = memberService.findByLoginId(loginId);
        model.addAttribute(SecurityConfig.LOGINUSER, loginUser);
        return "user/info";
    }

    @GetMapping("/infoSession")
    private String showInfoSession(Model model) {
        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        return "user/info";
    }
}
