package com.studymavernspringboot.mustachajax.security.dto;

import com.studymavernspringboot.mustachajax.commons.dto.BaseNullRequest;
import com.studymavernspringboot.mustachajax.member.IMember;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest extends BaseNullRequest implements IMember {
    private Long id;
    @Size(min = 3, max = 20, message = "이름은 3~20자 이어야 합니다.")
    private String name;
    @Size(min = 5, max = 20, message = "닉네임은 5~20자 이어야 합니다.")
    private String nickname;
    @Size(min = 8, max = 10, message = "로그인ID는 8~10자 이어야 합니다.")
    private String loginId;
    @Size(min = 8, max = 20, message = "암호는 8~20자 이어야 합니다.")
    private String password;
    @Size(min = 1, max = 150, message = "이메일은 1~150자 이어야 합니다.")
    private String email;
    @Null
    private String role;
    @Null
    private Boolean active;
}
