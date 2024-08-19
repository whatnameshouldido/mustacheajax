package com.studymavernspringboot.mustachajax.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j //log를 만들어준다.
@Controller //Web 용 Controller 이다. 화면을 그리거나 redirect 할 때 유용하다.
@RequestMapping("/catajx") //Controller의 url 앞부분이다.
public class CategoryAjxController {
//    @Autowired
//    private IMemberService memberService;

    @GetMapping("/category_ajx_list")
    // GET method로 /catajx/~~~~~~~~~ url 주소로 접속하도록 한다.
    public String category_ajx_list(Model model) {
//        IMember loginUser = (IMember)model.getAttribute(SecurityConfig.LOGINUSER);
//        if ( loginUser == null ) {
//            // 로그인 사용자가 아니면 리턴
//            return "redirect:/";
//        }
        return "catajx/category_ajx_list";
        //화면 템플릿 엔진의 화면파일 경로/파일명
        //=> resources/templates/catajx/category_ajx_list.html
    }
}