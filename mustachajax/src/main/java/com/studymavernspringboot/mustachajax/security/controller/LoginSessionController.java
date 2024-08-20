package com.studymavernspringboot.mustachajax.security.controller;

import com.studymavernspringboot.mustachajax.member.IMember;
import com.studymavernspringboot.mustachajax.member.IMemberService;
import com.studymavernspringboot.mustachajax.security.config.SecurityConfig;
import com.studymavernspringboot.mustachajax.security.dto.LoginRequest;
import com.studymavernspringboot.mustachajax.security.dto.SignUpRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/selogin")
public class LoginSessionController {
    @Autowired
    private IMemberService memberService;

    @GetMapping("/signup")
    private String viewSignUp() {
        return "login/signup";
    }

    @PostMapping("/signup")
    private String signUp(Model model, @Valid @ModelAttribute SignUpRequest dto, BindingResult bindingResult) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            if (bindingResult.hasErrors()) {
                List<String> errorList = new ArrayList<>();
                for (FieldError error : bindingResult.getFieldErrors()) {
                    errorList.add(error.getField() + " : " + error.getDefaultMessage());
                    log.info(error.getDefaultMessage());
                }
                model.addAttribute("errorList", errorList);
                return "login/fail";
            }
            this.memberService.addMember(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "회원 가입 실패 했습니다. 입력 정보를 다시 확인하거나 관리자에게 문의하세요");
            return "login/fail";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    private String viewLogin() {
        return "login/login";
    }

    @PostMapping("/signin")
    private String signin(Model model, @ModelAttribute LoginRequest dto
        , HttpServletRequest request) {
        try {
            if (dto == null) {
                return "redirect:/";
            }
            IMember loginUser = this.memberService.login(dto);
            if ( loginUser == null ) {
                model.addAttribute("message", "로그인 실패 실패 했습니다. ID와 암호를 확인하세요");
                return "login/fail";
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER, loginUser.getLoginId());
            session.setMaxInactiveInterval(60 * 60);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "로그인 실패 실패 했습니다. ID와 암호를 확인하세요");
            return "login/fail";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    private String logout(HttpServletResponse response) {
        return "login/signout";
    }

    @GetMapping("/signout")
    private String signout(HttpSession session) {
        session.invalidate();
        return "login/signout";
    }
}
