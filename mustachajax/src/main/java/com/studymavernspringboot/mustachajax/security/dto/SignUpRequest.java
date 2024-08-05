package com.studymavernspringboot.mustachajax.security.dto;

import com.studymavernspringboot.mustachajax.member.IMember;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest implements IMember {
    private Long id;
    private String name;
    private String loginId;
    private String password;
    private String email;
    private String role;
}
