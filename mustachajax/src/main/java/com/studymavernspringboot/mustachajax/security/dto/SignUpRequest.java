package com.studymavernspringboot.mustachajax.security.dto;

import com.studymavernspringboot.mustachajax.commons.dto.BaseNullRequest;
import com.studymavernspringboot.mustachajax.member.IMember;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest extends BaseNullRequest implements IMember {
    private Long id;
    private String name;
    private String nickname;
    private String loginId;
    private String password;
    private String email;
    private String role;
    private Boolean Active;
}
