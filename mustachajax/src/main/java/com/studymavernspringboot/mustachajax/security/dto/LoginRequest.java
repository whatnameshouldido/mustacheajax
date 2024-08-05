package com.studymavernspringboot.mustachajax.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class LoginRequest {
    private String loginId;
    private String password;
}
