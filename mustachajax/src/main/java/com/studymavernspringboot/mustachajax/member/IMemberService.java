package com.studymavernspringboot.mustachajax.member;

import com.studymavernspringboot.mustachajax.ICommonService;
import com.studymavernspringboot.mustachajax.SearchAjaxDto;
import com.studymavernspringboot.mustachajax.security.dto.SignUpRequest;

import java.util.List;

public interface IMemberService extends ICommonService<MemberDto> {
    IMember addMember(SignUpRequest dto);
    IMember findByLoginId(String loginId);
    List<IMember> findAllByLoginIdContains(SearchAjaxDto dto);
    int countAllByLoginIdContains(SearchAjaxDto searchMemberDto);
}
