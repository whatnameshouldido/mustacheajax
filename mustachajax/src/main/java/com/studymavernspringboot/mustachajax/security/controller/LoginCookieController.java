package com.studymavernspringboot.mustachajax.security.controller;

import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.IMemberService;
import com.studymavernspringboot.mustachajax.security.dto.LoginRequest;
import com.studymavernspringboot.mustachajax.security.dto.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("")
public class LoginCookieController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("")
    private String index() {
        return "index";
    }

    @GetMapping("/signup")
    private String viewSignUp() {
        return "login/signup";
    }

    @PostMapping("/signup")
    private String signUp(@ModelAttribute SignUpRequest dto) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            this.memberService.addMember(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    private String viewLogin() {
        return "login/login";
    }

    @PostMapping("/signin")
    private String signin(Model model, @ModelAttribute LoginRequest dto
        , HttpServletResponse response) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            IMember loginUser = this.memberService.login(dto);
            if ( loginUser != null ) {
                Cookie cookie = new Cookie("loginId", loginUser.getLoginId());
                cookie.setSecure(false);
                cookie.setPath("http://localhost:8088");
                cookie.setMaxAge(60 * 30);
                response.addCookie(cookie);

                model.addAttribute("loginUser", loginUser);
                return "redirect:/";
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "login/fail";
    }

    @GetMapping("/info")
    private String showInfo(Model model, @CookieValue(name = "loginId") String loginId) {
        IMember loginUser = memberService.findByLoginId(loginId);
        model.addAttribute("loginUser", loginUser);
        return "user/info";
    }

    @GetMapping("/signout")
    private String logout(HttpServletResponse response) {
//        Cookie cookie = new Cookie("loginId", null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
        return "login/logout";
    }
}
