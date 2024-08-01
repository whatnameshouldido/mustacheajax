package com.studymavernspringboot.mustachajax.category;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catweb")
public class CatWebController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/category_list")
    public String categoryList(Model model,
                               @RequestParam int page, @RequestParam String searchName) {
        try {
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .page(page).searchName(searchName).build();
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            List<ICategory> list = this.categoryService.findAllByNameContains(searchCategoryDto);
            searchCategoryDto.setTotal(total);
            model.addAttribute("categoryList", list);
            model.addAttribute("searchCategoryDto", searchCategoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "catweb/category_list";
    }

    @PostMapping("/category_insert")
    public String categoryInsert(@ModelAttribute CategoryDto dto) {
        try {
            this.categoryService.insert(dto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:category_list?page=1&searchName=";  // 브라우저 주소를 redirect 한다.
    }

    @GetMapping("/category_view")    // 브라우저의 URL 주소
    public String categoryView(Model model, @RequestParam Long id) {
        try {
            ICategory categoryDto = this.categoryService.findById(id);
            model.addAttribute("categoryDto", categoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "catweb/category_view";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/category_update")
    public String categoryUpdate(@ModelAttribute CategoryDto dto) {
        try {
            this.categoryService.update(dto.getId(), dto);
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:category_list?page=1&searchName=";
    }

    @GetMapping("/category_delete")
    public String categoryDelete(@RequestParam Long id) {
        try {
            this.categoryService.remove(id);
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "redirect:category_list?page=1&searchName=";
    }

    @GetMapping("/category_add")
    public String categoryAdd(Model model) {
        try {

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "catweb/category_add";
    }
}
