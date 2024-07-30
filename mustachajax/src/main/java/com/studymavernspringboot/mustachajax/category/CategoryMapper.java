package com.studymavernspringboot.mustachajax.category;//package com.softagape.mybatisprj.category;
//
//import org.apache.ibatis.annotations.*;
//
//import java.util.List;
//
//@Mapper
//public interface CategoryMapper {
//    @Insert("INSERT INTO category_tbl(name) VALUES(#{name})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    void insert(CategoryDto dto);
//
//    @Select("SELECT id, name FROM category_tbl WHERE id = #{id}")
//    CategoryDto findById(long id);
//
//    @Select("SELECT id, name FROM category_tbl WHERE name = #{name}")
//    CategoryDto findByName(String name);
//
//    @Select("SELECT id, name FROM category_tbl")
//    List<CategoryDto> findAll();
//
//    @Delete("DELETE FROM category_tbl WHERE id = #{id}")
//    void deleteById(Long id);
//
//    @Update("UPDATE category_tbl SET name = #{name} WHERE id = #{id}")
//    void update(CategoryDto dto);
//
//    @Select("SELECT id, name FROM category_tbl WHERE name LIKE '%#{name}%'")
//    List<CategoryDto> findAllByNameContains(String name);
//}
