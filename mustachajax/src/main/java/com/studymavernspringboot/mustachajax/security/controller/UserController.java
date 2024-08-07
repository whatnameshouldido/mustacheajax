package com.studymavernspringboot.mustachajax.security.controller;

import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("/info")
    private String showInfo(Model model, @CookieValue(name = "loginId") String loginId) {
        IMember loginUser = memberService.findByLoginId(loginId);
        model.addAttribute("loginUser", loginUser);
        return "user/info";
    }
}
