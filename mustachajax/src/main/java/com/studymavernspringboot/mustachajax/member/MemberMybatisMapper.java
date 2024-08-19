package com.studymavernspringboot.mustachajax.member;

import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMybatisMapper {
    void insert(MemberDto dto);
    void update(MemberDto dto);
    void delete(Long id);
    MemberDto findById(Long id);
    MemberDto findByLoginId(String loginId);
    MemberDto findByNickname(String nickname);
    int countAllByLoginIdContains(SearchAjaxDto dto);
    List<IMember> findAllByLoginIdContains(SearchAjaxDto dto);
}
