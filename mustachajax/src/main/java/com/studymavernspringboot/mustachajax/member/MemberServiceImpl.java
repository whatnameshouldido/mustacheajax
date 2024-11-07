package com.studymavernspringboot.mustachajax.member;

import com.studymavernspringboot.mustachajax.commons.dto.CUDInfoDto;
import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
import com.studymavernspringboot.mustachajax.commons.exception.IdNotFoundException;
import com.studymavernspringboot.mustachajax.security.dto.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberMybatisMapper memberMybatisMapper;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public IMember insert(CUDInfoDto cudInfoDto, IMember member) {
        if ( !this.isValidInsert(member) ) {
            return null;
        }
        MemberDto dto = MemberDto.builder().build();
        dto.copyFields(member);
        dto.setPassword(encoder.encode(dto.getPassword()));
        dto.setRole(MemberRole.USER.toString());
        dto.setActive(false);
        cudInfoDto.setCreateInfo(dto);
        this.memberMybatisMapper.insert(dto);
        return dto;
    }

    private boolean isValidInsert(IMember dto) {
        if (dto == null) {
            return false;
        } else if ( dto.getName() == null || dto.getName().isEmpty() ) {
            return false;
        } else if ( dto.getNickname() == null || dto.getNickname().isEmpty() ) {
            return false;
        } else if ( dto.getLoginId() == null || dto.getLoginId().isEmpty() ) {
            return false;
        } else if ( dto.getPassword() == null || dto.getPassword().isEmpty() ) {
            return false;
        } else if ( dto.getEmail() == null || dto.getEmail().isEmpty() ) {
            return false;
//        } else if ( dto.getRole() == null ) {
//            return false;
//        } else if ( dto.getActive() == null ) {
//            return false;
        }
        return true;
    }

    @Override
    public IMember update(CUDInfoDto cudInfoDto, IMember member) {
        if ( member == null || member.getId() == null || member.getId() <= 0 ) {
            return null;
        }
        IMember find = this.findById(member.getId());
        find.copyFields(member);
        cudInfoDto.setUpdateInfo(find);
        this.memberMybatisMapper.update((MemberDto) find);
        return find;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, IMember member) {
        if ( member == null || member.getId() == null || member.getId() <= 0 ) {
            return false;
        }
        MemberDto find = this.memberMybatisMapper.findById(member.getId());
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", member.getId()));
        }
        find.copyFields(member);
        cudInfoDto.setDeleteInfo(find);
        this.memberMybatisMapper.updateDeleteFlag(find);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        MemberDto find = this.memberMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        this.memberMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public IMember findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        MemberDto find = this.memberMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public IMember findByLoginId(String loginId) {
        if ( loginId == null || loginId.isEmpty() ) {
            return null;
        }
        MemberDto find = this.memberMybatisMapper.findByLoginId(loginId);
        return find;
    }

    @Override
    public IMember findByNickname(String nickname) {
        if ( nickname == null || nickname.isEmpty() ) {
            return null;
        }
        MemberDto find = this.memberMybatisMapper.findByNickname(nickname);
        return find;
    }

    @Override
    public IMember login(LoginRequest loginRequest) {
        if ( loginRequest == null || loginRequest.getLoginId() == null
                || loginRequest.getPassword() == null) {
            return null;
        }
        IMember member = this.memberMybatisMapper.findByLoginId(loginRequest.getLoginId());
        if ( member == null || !encoder.matches(loginRequest.getPassword(),
                member.getPassword()) ) {
            return null;
        }
        return member;
    }

    @Override
    public Boolean changePassword(IMember member) throws Exception {
        if ( member == null ) {
            return false;
        }
        MemberDto dto = new MemberDto();
        dto.copyFields(member);
        dto.setPassword(encoder.encode(dto.getPassword()));
        this.memberMybatisMapper.changePassword(dto);
        return true;
    }

    private List<IMember> getIMemberList(List<MemberDto> list) {
        if ( list == null || list.size() <= 0 ) {
            return new ArrayList<>();
        }
        // input : [MemberDto|MemberDto|MemberDto|MemberDto|MemberDto]
//        List<IMember> result = new ArrayList<>();
//        for( MemberDto item : list ) {
//            result.add( (IMember)item );
//        }
        // output : [IMember|IMember|IMember|IMember|IMember]
        List<IMember> result = list.stream()
                .map(item -> (IMember)item)
                .toList();
        // return : [IMember|IMember|IMember|IMember|IMember]
        return result;
    }

    @Override
    public Integer countAllByNameContains(SearchAjaxDto searchCategoryDto) {
        return this.memberMybatisMapper.countAllByNameContains(searchCategoryDto);
    }

    @Override
    public List<IMember> findAllByNameContains(SearchAjaxDto dto) {
        if (dto == null) {
            return List.of();
        }
        dto.settingValues();
        List<IMember> result = this.getIMemberList(
                this.memberMybatisMapper.findAllByNameContains(dto)
        );
        return result;
    }
}
