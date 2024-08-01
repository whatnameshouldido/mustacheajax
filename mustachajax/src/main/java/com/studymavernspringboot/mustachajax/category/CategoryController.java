package com.studymavernspringboot.mustachajax.category;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j //log를 만들어 준다.
@RestController //RestFul API 용 Controller이다. JSON 문자형식으로 요청/응답한다.
@RequestMapping("/cat") //Controller의 url 앞부분이다.
public class CategoryController {
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired //SpringBoot가 CategoryServiceImpl 데이터형으로 객체를 자동 생성한다.
    private CategoryServiceImpl categoryService;

    @PostMapping //POST method : /cat
    public ResponseEntity<ICategory> insert(@RequestBody CategoryDto dto) {
        // ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        // @RequestBody CategoryDto dto : JSON 문자열로 요청을 받는다. 다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야 한다.
        // 다만 JSON 문자열의 데이터가 CategoryDto 데이터형이어야한다.
        try {
            if (dto == null) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            ICategory result = this.categoryService.insert(dto);
            //최종 목적지인 Mybatis 쿼리를 DB에 실행한다.
            if (result == null) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            return ResponseEntity.ok(result);
            //200 OK와 result 데이터를 응답한다.
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build(); //error 응답
        }
    }

    @GetMapping //GET method : /cat
    public ResponseEntity<List<ICategory>> getAll() {
        //ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        //List<ICategory> 데이터형 리턴은 배열 데이터를 JSON 문자열로 표현하여 리턴한다.
        // [{"id":값, "name":"값"}, {"id":값, "name":"값"}, {"id":값, "name":"값"}, ....]
        try {
            List<ICategory> result = this.categoryService.getAllList();
            //최종 목적지인 Mybatis 쿼리를 DB에 실행하고 결과를 리턴 받는다.
            return ResponseEntity.ok(result);
            //200 OK와 result 데이터를 응답한다.
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build(); //error 응답
        }
    }

    @DeleteMapping("/{id}") //DELETE method : /ct/번호
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        //ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        //@PathVariable Long id : URL 주소와 /ct/번호 => {id} id 변수의 값으로 요청된다.
        try {
            if(id == null) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            Boolean result = this.categoryService.remove(id);
            //최종 목적지인 Mybatis 쿼리를 DB에 실행하고 결과를 리턴 받는다.
            return ResponseEntity.ok(result);
            //200 OK와 result 데이터를 응답한다.
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}") //PATCH method : /ct/번호
    public ResponseEntity<ICategory> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        //ResponseEntity<데이터형> : http 응답을 http 응답코드와 리턴데이터형으로 묶어서 응답한다.
        //@PathVariable Long id
        try {
            if (id == null || dto == null) {
                return ResponseEntity.badRequest().build(); //error 응답
            }
            ICategory result = this.categoryService.update(id, dto);
            if (result == null) {
                return ResponseEntity.notFound().build(); //error 응답
            }
            return ResponseEntity.ok(result);
            //200 OK와 result 데이터를 응답한다.
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build(); //error 응답
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ICategory> findById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().build();
            }
            ICategory result = this.categoryService.findById(id);
            if(result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{searchName}")
    public ResponseEntity<List<ICategory>> findAllByNameContains(@PathVariable String searchName) {
        try {
            if(searchName == null || searchName.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .searchName(searchName).page(1).build();
            List<ICategory> result = this.categoryService.findAllByNameContains(searchCategoryDto);
            if (result == null || result.size() <= 0) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/searchName")
    public ResponseEntity<SearchCategoryDto> findAllByNameContains(@RequestBody SearchCategoryDto searchCategoryDto) {
        try {
            if ( searchCategoryDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            List<ICategory> list = this.categoryService.findAllByNameContains(searchCategoryDto);
            if ( list == null ) {
                return ResponseEntity.notFound().build();
            }
            searchCategoryDto.setTotal(total);
            searchCategoryDto.setDataList(list);
            return ResponseEntity.ok(searchCategoryDto);
        } catch ( Exception ex ) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/countName")
    public ResponseEntity<Integer> countAllByNameContains(@RequestBody SearchCategoryDto searchCategoryDto) {
        try {
            if ( searchCategoryDto == null ) {
                return ResponseEntity.badRequest().build();
            }
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            return ResponseEntity.ok(total);
        } catch ( Exception ex ) {
            logger.error(ex.toString());
            return ResponseEntity.badRequest().build();
        }
    }
}
